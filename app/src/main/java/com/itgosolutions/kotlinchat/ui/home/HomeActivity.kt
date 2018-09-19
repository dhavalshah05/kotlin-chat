package com.itgosolutions.kotlinchat.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.model.LatestMessage
import com.itgosolutions.kotlinchat.model.User
import com.itgosolutions.kotlinchat.ui.chatLog.ChatLogActivity
import com.itgosolutions.kotlinchat.ui.common.BaseActivity
import com.itgosolutions.kotlinchat.ui.login.LoginActivity
import com.itgosolutions.kotlinchat.ui.selectUser.SelectUserActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class HomeActivity : BaseActivity(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter
    private val _adapter = LatestMessageAdapter()

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

        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun initRecyclerView() {
        latestMessagesRecyclerView.layoutManager = LinearLayoutManager(this)
        latestMessagesRecyclerView.adapter = _adapter
        _adapter.setOnItemClickListener {
            presenter.onLatestMessageRowClicked(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_screen_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuActionLogout -> {
                presenter.logout()
                return true
            }
            R.id.menuActionSelectUser -> {
                SelectUserActivity.start(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
        presenter.initView(this)
    }

    override fun destroyView() {
        presenter.destroyView()
    }

    override fun setScreenTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun clearLatestMessages() {
        _adapter.clear()
    }

    override fun addLatestMessage(latestMessage: LatestMessage, previousChildKey: String?) {
        _adapter.addLatestMessage(latestMessage, previousChildKey)
    }

    override fun updateLatestMessage(latestMessage: LatestMessage) {
        _adapter.updateLatestMessage(latestMessage)
    }

    override fun showNoLatestMessagesFoundMessage() {

    }

    override fun startLoginScreen() {
        LoginActivity.start(this)
        finish()
    }

    override fun startChatLogScreen(user: User) {
        ChatLogActivity.start(this, user)
    }
}
