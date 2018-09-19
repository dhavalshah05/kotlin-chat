package com.itgosolutions.kotlinchat.ui.home

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.itgosolutions.kotlinchat.manager.UserManager
import com.itgosolutions.kotlinchat.model.ChatMessage
import com.itgosolutions.kotlinchat.model.LatestMessage
import com.itgosolutions.kotlinchat.model.User

class HomePresenter(private val _auth: FirebaseAuth,
                    private val _database: FirebaseDatabase,
                    private val _userManager: UserManager) : HomeContract.Presenter {

    private var _view: HomeContract.View? = null

    override fun initView(view: HomeContract.View) {
        _view = view
        setScreenTitle()
        validateLogin()
    }

    override fun destroyView() {
        _view = null
    }

    override fun logout() {
        _auth.signOut()
        _userManager.logoutUser()
        _view?.startLoginScreen()
    }

    private fun setScreenTitle() {
        _view?.setScreenTitle("Kotlin Chat")
    }

    private fun validateLogin() {
        when {
            _userManager.isLoggedIn -> loadLatestMessages()
            _auth.currentUser != null -> {
                val databaseRef = _database.reference.child("users").child("${_auth.currentUser?.uid}")
                databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            _userManager.loggedInUser = user
                            loadLatestMessages()
                        }
                    }
                })
            }
            else -> _view?.startLoginScreen()
        }
    }

    override fun onLatestMessageRowClicked(selectedUserId: String) {
        getUserById(selectedUserId) {
            _view?.startChatLogScreen(it)
        }
    }

    private fun loadLatestMessages() {
        _view?.clearLatestMessages()

        val messagesRef = _database.reference.child("latest_messages").child(_userManager.loggedInUser!!.uid)

        messagesRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(p0: DataSnapshot, previousChildKey: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                val chatPartnerId = getChatPartnerId(chatMessage)

                getUserById(chatPartnerId) { user ->
                    val latestMessage = LatestMessage(p0.key!!, chatMessage, user.username, user.profileUrl)
                    _view?.addLatestMessage(latestMessage, previousChildKey)
                }
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java) ?: return
                val chatPartnerId = getChatPartnerId(chatMessage)

                getUserById(chatPartnerId) { user ->
                    val latestMessage = LatestMessage(p0.key!!, chatMessage, user.username, user.profileUrl)
                    _view?.updateLatestMessage(latestMessage)
                }

            }

            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
            override fun onChildRemoved(p0: DataSnapshot) {}

        })

    }

    private fun getChatPartnerId(chatMessage: ChatMessage): String {
        return if (chatMessage.fromId == _userManager.loggedInUser!!.uid) {
            chatMessage.toId
        } else {
            chatMessage.fromId
        }
    }

    private fun getUserById(id: String, success: (user: User) -> Unit) {
        val userRef = _database.reference.child("users").child(id)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java) ?: return
                success(user)
            }
        })
    }

}