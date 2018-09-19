package com.itgosolutions.kotlinchat.ui.selectUser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.model.User
import com.itgosolutions.kotlinchat.ui.chatLog.ChatLogActivity
import com.itgosolutions.kotlinchat.ui.common.BaseActivity
import kotlinx.android.synthetic.main.activity_select_user.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class SelectUserActivity : BaseActivity(), SelectUserContract.View {

    @Inject
    lateinit var presenter: SelectUserContract.Presenter

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SelectUserActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_user)
        getActivityComponent().inject(this)

        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Select User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
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

    override fun showLoading() {
        progress.visibility = VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = GONE
    }

    override fun showNoUserFoundMessage() {
        noUserFoundTextView.visibility = VISIBLE
    }

    override fun hideNoUserFoundMessage() {
        noUserFoundTextView.visibility = GONE
    }

    override fun showUsers(users: List<User>) {
        selectUserRecyclerView.visibility = VISIBLE
        selectUserRecyclerView.layoutManager = LinearLayoutManager(this)
        selectUserRecyclerView.setHasFixedSize(true)

        val adapter = SelectUserAdapter(users)
        adapter.setOnItemClickListener { startChatLogScreen(it) }

        selectUserRecyclerView.adapter = adapter
    }

    override fun hideUsers() {
        selectUserRecyclerView.visibility = GONE
    }

    private fun startChatLogScreen(user: User) {
        ChatLogActivity.start(this, user)
        finish()
    }
}
