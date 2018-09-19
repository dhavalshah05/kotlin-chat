package com.itgosolutions.kotlinchat.ui.chatLog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.model.ChatMessage
import com.itgosolutions.kotlinchat.model.User
import com.itgosolutions.kotlinchat.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ChatLogActivity : BaseActivity(), ChatLogContract.View {

    @Inject
    lateinit var presenter: ChatLogContract.Presenter

    private val _adapter = ChatLogAdapter()

    companion object {
        private const val EXTRA_USER = "extra_user"

        fun start(context: Context, user: User) {
            val intent = Intent(context, ChatLogActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        getActivityComponent().inject(this)

        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        presenter.setSelectedUser(user)

        initToolbar()
        initRecyclerView()
        sendButton.setOnClickListener { presenter.sendMessage(newMessageEditText.text.toString()) }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {
        chatLogRecyclerView.layoutManager = LinearLayoutManager(this)
        chatLogRecyclerView.adapter = _adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setScreenTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun initView() {
        presenter.initView(this)
    }

    override fun destroyView() {
        presenter.destroyView()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(chatMessage: ChatMessage) {
        _adapter.showMessage(chatMessage)
    }

    override fun clearNewMessageEditText() {
        newMessageEditText.text.clear()
    }

    override fun scrollChatLogListToBottom() {
        chatLogRecyclerView.scrollToPosition(_adapter.itemCount - 1)
    }
}
