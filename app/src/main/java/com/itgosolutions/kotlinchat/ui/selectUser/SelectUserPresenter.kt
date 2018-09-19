package com.itgosolutions.kotlinchat.ui.selectUser

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itgosolutions.kotlinchat.manager.UserManager
import com.itgosolutions.kotlinchat.model.User

class SelectUserPresenter(private val _database: FirebaseDatabase,
                          private val userManager: UserManager) : SelectUserContract.Presenter {

    private var _view: SelectUserContract.View? = null

    override fun initView(view: SelectUserContract.View) {
        _view = view
        loadUsers()
    }

    override fun destroyView() {
        _view = null
    }

    private fun loadUsers() {
        val userRef = _database.reference.child("users")
        val users = mutableListOf<User>()

        startLoading()

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children
                        .map { it.getValue(User::class.java) }
                        .filter { it?.uid != userManager.loggedInUser?.uid }
                        .sortedBy { it?.username }
                        .forEach {
                            if (it != null)
                                users.add(it)
                        }

                if (users.size > 0)
                    showSuccess(users)
                else
                    showNoUserFound()
            }
        })
    }

    private fun startLoading() {
        _view?.hideUsers()
        _view?.hideNoUserFoundMessage()
        _view?.showLoading()
    }


    private fun showSuccess(users: List<User>) {
        _view?.hideLoading()
        _view?.hideNoUserFoundMessage()
        _view?.showUsers(users)
    }

    private fun showNoUserFound() {
        _view?.hideLoading()
        _view?.hideUsers()
        _view?.showNoUserFoundMessage()
    }
}