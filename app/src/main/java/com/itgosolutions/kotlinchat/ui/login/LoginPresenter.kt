package com.itgosolutions.kotlinchat.ui.login

import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itgosolutions.kotlinchat.manager.UserManager
import com.itgosolutions.kotlinchat.model.User

class LoginPresenter(private val _auth: FirebaseAuth,
                     private val _database: FirebaseDatabase,
                     private val _userManager: UserManager) : LoginContract.Presenter {

    private var _view: LoginContract.View? = null

    override fun initView(view: LoginContract.View) {
        _view = view
    }

    override fun destroyView() {
        _view = null
    }

    override fun loginUser(email: String, password: String) {
        if (TextUtils.isEmpty(email.trim())) {
            _view?.showEmailError("Please provide valid email")
            return
        }

        if (TextUtils.isEmpty(password.trim())) {
            _view?.showPasswordError("Please provide valid password")
            return
        }

        startLoading()

        _auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    if (authResult != null && authResult.user != null) {

                        val databaseRef = _database.reference.child("users").child("${authResult.user?.uid}")
                        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onCancelled(p0: DatabaseError) {

                            }

                            override fun onDataChange(snapshot: DataSnapshot) {
                                val user = snapshot.getValue(User::class.java)
                                if (user != null) {
                                    _userManager.loggedInUser = user
                                    showSuccess()
                                }

                            }
                        })
                    } else {
                        showFailure("Login Failed")
                    }
                }
                .addOnFailureListener { _ ->
                    showFailure("Invalid Credentials")
                }
    }

    private fun startLoading() {
        _view?.showLoading()
        _view?.disableLoginButton()
        _view?.disableRegisterInfoContainer()
    }

    private fun stopLoading() {
        _view?.hideLoading()
        _view?.enableLoginButton()
        _view?.enableRegisterInfoContainer()
    }

    private fun showSuccess() {
        stopLoading()
        _view?.startHomeScreen()
    }

    private fun showFailure(errorMessage: String) {
        stopLoading()
        _view?.showLoginError(errorMessage)
    }
}