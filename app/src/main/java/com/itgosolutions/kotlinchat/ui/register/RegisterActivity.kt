package com.itgosolutions.kotlinchat.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.ui.common.BaseActivity
import com.itgosolutions.kotlinchat.ui.common.setSystemBarColor
import com.itgosolutions.kotlinchat.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    companion object {
        fun start(context: Context){
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getActivityComponent().inject(this)

        setSystemBarColor(R.color.blue_grey_900)

        registerButton.setOnClickListener { registerUser() }
        loginTextView.setOnClickListener { startLoginScreenAndFinish() }
    }

    private fun startLoginScreenAndFinish() {
        LoginActivity.start(this)
        finish()
    }

    private fun registerUser() {
        val username = usernameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

    }
}
