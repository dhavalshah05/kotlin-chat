package com.itgosolutions.kotlinchat.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.*
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.ui.common.BaseActivity
import com.itgosolutions.kotlinchat.ui.common.setSystemBarColor
import com.itgosolutions.kotlinchat.ui.common.showShortToast
import com.itgosolutions.kotlinchat.ui.home.HomeActivity
import com.itgosolutions.kotlinchat.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity(), LoginContract.View {

    @Inject
    lateinit var presenter: LoginContract.Presenter

    companion object {
        fun start(context: Context){
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getActivityComponent().inject(this)

        setSystemBarColor(R.color.blue_grey_900)

        loginButton.setOnClickListener { loginUser() }
        registerTextView.setOnClickListener { startRegisterScreenAndFinish() }
    }

    override fun initView() {
        presenter.initView(this)
    }

    override fun destroyView() {
        presenter.destroyView()
    }

    override fun showLoading() {
        progress.visibility = VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = GONE
    }

    override fun enableLoginButton() {
        loginButton.visibility = VISIBLE
    }

    override fun disableLoginButton() {
        loginButton.visibility = INVISIBLE
    }

    override fun enableRegisterInfoContainer() {
        registerInfoContainer.visibility = VISIBLE
    }

    override fun disableRegisterInfoContainer() {
        registerInfoContainer.visibility = INVISIBLE
    }

    override fun showEmailError(errorMessage: String) {
        showShortToast(errorMessage)
    }

    override fun showPasswordError(errorMessage: String) {
        showShortToast(errorMessage)
    }

    override fun startHomeScreen() {
        HomeActivity.start(this)
        finish()
    }

    override fun showLoginError(errorMessage: String) {
        showShortToast(errorMessage)
    }

    private fun startRegisterScreenAndFinish() {
        RegisterActivity.start(this)
        finish()
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        presenter.loginUser(email, password)
    }
}
