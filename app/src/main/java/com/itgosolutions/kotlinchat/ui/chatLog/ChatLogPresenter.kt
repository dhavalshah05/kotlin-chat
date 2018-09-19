package com.itgosolutions.kotlinchat.ui.chatLog

import android.text.TextUtils
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.itgosolutions.kotlinchat.manager.UserManager
import com.itgosolutions.kotlinchat.model.ChatMessage
import com.itgosolutions.kotlinchat.model.MessageType
import com.itgosolutions.kotlinchat.model.User

class ChatLogPresenter(private val _database: FirebaseDatabase,
                       private val _userManager: UserManager) : ChatLogContract.Presenter {

    private var _view: ChatLogContract.View? = null
    private lateinit var _selectedUser: User

    override fun initView(view: ChatLogContract.View) {
        _view = view
        setScreenTitle()
        loadMessages()
    }

    override fun destroyView() {
        _view = null
    }

    override fun setSelectedUser(user: User) {
        _selectedUser = user
    }

    override fun sendMessage(message: String) {
        if (TextUtils.isEmpty(message.trim()))
            return

        val key = _database.reference.child("user_messages").child(_userManager.loggedInUser!!.uid).child(_selectedUser.uid).push().key

        val chatMessage = ChatMessage(key!!, message, _userManager.loggedInUser!!.uid, _selectedUser.uid)

        val messageFromPath = "/user-messages/${_userManager.loggedInUser!!.uid}/${_selectedUser.uid}/$key!!"
        val messageToPath = "/user-messages/${_selectedUser.uid}/${_userManager.loggedInUser!!.uid}/$key!!"
        val latestMessageFromPath = "/latest_messages/${_userManager.loggedInUser!!.uid}/${_selectedUser.uid}"
        val latestMessageToPath = "/latest_messages/${_selectedUser.uid}/${_userManager.loggedInUser!!.uid}"

        val refMap = mutableMapOf<String, Any>()
        refMap[messageFromPath] = chatMessage
        refMap[messageToPath] = chatMessage
        refMap[latestMessageFromPath] = chatMessage
        refMap[latestMessageToPath] = chatMessage

        _database.reference.updateChildren(refMap)
        _view?.clearNewMessageEditText()
    }

    private fun setScreenTitle() {
        _view?.setScreenTitle(_selectedUser.username)
    }

    private fun loadMessages() {
        val messagesRef = _database.reference.child("user-messages").child(_userManager.loggedInUser!!.uid).child(_selectedUser.uid)

        messagesRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {}

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                chatMessage?.let {
                    if (it.fromId == _userManager.loggedInUser?.uid) {
                        it.type = MessageType.FROM
                        it.profileUrl = _userManager.loggedInUser!!.profileUrl
                    } else {
                        it.type = MessageType.TO
                        it.profileUrl = _selectedUser.profileUrl
                    }
                    _view?.showMessage(chatMessage)
                    _view?.scrollChatLogListToBottom()
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {}
        })
    }
}