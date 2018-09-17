package com.itgosolutions.kotlinchat.ui.login

interface LoginContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun enableLoginButton()
        fun disableLoginButton()
        fun enableRegisterInfoContainer()
        fun disableRegisterInfoContainer()
        fun showEmailError(errorMessage: String)
        fun showPasswordError(errorMessage: String)
        fun onLoginSuccess()
        fun onLoginFailure(errorMessage: String)
    }

    interface Presenter {
        fun initView(view: LoginContract.View)
        fun destroyView()
        fun loginUser(email: String, password: String)
    }

}