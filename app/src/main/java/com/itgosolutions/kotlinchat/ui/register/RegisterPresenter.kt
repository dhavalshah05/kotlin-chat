package com.itgosolutions.kotlinchat.ui.register

import android.net.Uri
import android.text.TextUtils
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.itgosolutions.kotlinchat.manager.UserManager
import com.itgosolutions.kotlinchat.model.User
import java.io.File

class RegisterPresenter(private val _auth: FirebaseAuth,
                        private val _database: FirebaseDatabase,
                        private val _storage: FirebaseStorage,
                        private val _userManager: UserManager) : RegisterContract.Presenter {

    private var _view: RegisterContract.View? = null

    override fun initView(view: RegisterContract.View) {
        _view = view
    }

    override fun destroyView() {
        _view = null
    }

    override fun registerUser(username: String, email: String, password: String, imageFile: File?) {
        if (TextUtils.isEmpty(username.trim())) {
            _view?.showUsernameError("Please provide valid username")
            return
        }
        if (TextUtils.isEmpty(email.trim())) {
            _view?.showEmailError("Please provide valid email")
            return
        }
        if (TextUtils.isEmpty(password.trim()) || password.trim().length < 6) {
            _view?.showPasswordError("Password must be at least six characters long")
            return
        }

        startLoading()

        createUserWithEmailAndPassword(email, password,
                success = { uid ->
                    if (imageFile != null) {
                        uploadProfilePicture(uid, imageFile,
                                success = { url ->

                                    val user = User(uid, username, email, url)
                                    saveUserToFirebase(user,
                                            success = {
                                                _userManager.loggedInUser = user
                                                showSuccess()
                                            },
                                            failed = { showFailure("Registration Failed") }
                                    )

                                },
                                failed = { showFailure("Registration Failed") }
                        )
                    } else {
                        val user = User(uid, username, email)
                        saveUserToFirebase(user,
                                success = {
                                    _userManager.loggedInUser = user
                                    showSuccess()
                                },
                                failed = { showFailure("Registration Failed") }
                        )
                    }
                },
                failed = { showFailure("Registration Failed") }
        )
    }

    private fun createUserWithEmailAndPassword(email: String,
                                               password: String,
                                               success: (uid: String) -> Unit,
                                               failed: () -> Unit) {
        _auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult: AuthResult? ->
                    if (authResult != null) {
                        val firebaseUser: FirebaseUser? = authResult.user
                        if (firebaseUser != null) {
                            success(firebaseUser.uid)
                        }
                    } else {
                        failed()
                    }
                }
                .addOnFailureListener { _ ->
                    failed()
                }
    }

    private fun uploadProfilePicture(fileName: String,
                                     imageFile: File,
                                     success: (downloadableUrl: String) -> Unit,
                                     failed: () -> Unit) {
        val fileRef = _storage.getReference("profile_pics/ $fileName.jpg")
        val uploadTask = fileRef.putFile(Uri.fromFile(imageFile))

        uploadTask
                .addOnSuccessListener { taskSnapshot ->
                    if (taskSnapshot != null) {
                        fileRef.downloadUrl
                                .addOnCompleteListener { task ->
                                    val uri = task.result
                                    success(uri.toString())
                                }
                    }
                }
                .addOnFailureListener { _ ->
                    failed()
                }
    }

    private fun saveUserToFirebase(user: User,
                                   success: () -> Unit,
                                   failed: () -> Unit) {
        val databaseRef = _database.getReference("users")
        databaseRef.child(user.uid).setValue(user)
                .addOnCompleteListener { task: Task<Void> ->
                    if (task.isSuccessful) {
                        success()
                    } else {
                        failed()
                    }
                }
    }

    private fun startLoading() {
        _view?.showLoading()
        _view?.disableRegisterButton()
        _view?.disableLoginInfoContainer()
    }

    private fun stopLoading() {
        _view?.hideLoading()
        _view?.enableRegisterButton()
        _view?.enableLoginInfoContainer()
    }

    private fun showSuccess() {
        stopLoading()
        _view?.startHomeScreen()
    }

    private fun showFailure(errorMessage: String) {
        stopLoading()
        _view?.showRegistrationError(errorMessage)
    }
}