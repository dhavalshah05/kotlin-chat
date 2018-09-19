package com.itgosolutions.kotlinchat.ui.home

import com.itgosolutions.kotlinchat.model.LatestMessage
import com.itgosolutions.kotlinchat.model.User

interface HomeContract {

    interface View {
        fun setScreenTitle(title: String)
        fun showLoading()
        fun hideLoading()
        fun clearLatestMessages()
        fun showNoLatestMessagesFoundMessage()
        fun addLatestMessage(latestMessage: LatestMessage, previousChildKey: String?)
        fun updateLatestMessage(latestMessage: LatestMessage)
        fun startLoginScreen()
        fun startChatLogScreen(user: User)
    }

    interface Presenter {
        fun initView(view: HomeContract.View)
        fun destroyView()
        fun onLatestMessageRowClicked(selectedUserId: String)
        fun logout()
    }
}