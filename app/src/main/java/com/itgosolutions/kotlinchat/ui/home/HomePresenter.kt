package com.itgosolutions.kotlinchat.ui.home

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itgosolutions.kotlinchat.manager.UserManager
import com.itgosolutions.kotlinchat.model.User

class HomePresenter(private val _auth: FirebaseAuth,
                    private val _database: FirebaseDatabase,
                    private val _userManager: UserManager) : HomeContract.Presenter {

    private var _view: HomeContract.View? = null

    override fun initView(view: HomeContract.View) {
        _view = view
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

    private fun validateLogin() {
        when {
            _userManager.isLoggedIn -> _view?.setUsername("Hello ${_userManager.loggedInUser?.username}")
            _auth.currentUser != null -> {
                val databaseRef = _database.reference.child("users").child("${_auth.currentUser?.uid}")
                databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val user = snapshot.getValue(User::class.java)
                        if (user != null) {
                            _userManager.loggedInUser = user
                            _view?.setUsername("Hello ${user.username}")
                        }

                    }
                })
            }
            else -> _view?.startLoginScreen()
        }
    }
}