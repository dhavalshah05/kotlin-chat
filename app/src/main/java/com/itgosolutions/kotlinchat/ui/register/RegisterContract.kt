package com.itgosolutions.kotlinchat.ui.register

import java.io.File

interface RegisterContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun enableRegisterButton()
        fun disableRegisterButton()
        fun enableLoginInfoContainer()
        fun disableLoginInfoContainer()
        fun showUsernameError(errorMessage: String)
        fun showEmailError(errorMessage: String)
        fun showPasswordError(errorMessage: String)
        fun onRegisterSuccess()
        fun onRegisterFailure(errorMessage: String)
    }

    interface Presenter {
        fun initView(view: RegisterContract.View)
        fun destroyView()
        fun registerUser(username: String,
                         email: String,
                         password: String,
                         imageFile: File?)
    }

}