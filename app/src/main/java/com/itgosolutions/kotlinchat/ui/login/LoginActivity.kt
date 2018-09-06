package com.itgosolutions.kotlinchat.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.ui.common.BaseActivity
import com.itgosolutions.kotlinchat.ui.common.setSystemBarColor
import com.itgosolutions.kotlinchat.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

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

    private fun startRegisterScreenAndFinish() {
        RegisterActivity.start(this)
        finish()
    }

    private fun loginUser() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        //Login User
    }
}
