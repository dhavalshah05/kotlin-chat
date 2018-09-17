package com.itgosolutions.kotlinchat.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.ui.common.BaseActivity
import com.itgosolutions.kotlinchat.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getActivityComponent().inject(this)

        logoutButton.setOnClickListener { presenter.logout() }
    }

    override fun initView() {
        presenter.initView(this)
    }

    override fun destroyView() {
        presenter.destroyView()
    }

    override fun setUsername(username: String) {
        usernameTextVIew.text = username
    }

    override fun startLoginScreen() {
        LoginActivity.start(this)
        finish()
    }
}
