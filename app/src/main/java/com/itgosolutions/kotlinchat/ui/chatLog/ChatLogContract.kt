package com.itgosolutions.kotlinchat.ui.chatLog

import com.itgosolutions.kotlinchat.model.ChatMessage
import com.itgosolutions.kotlinchat.model.User

interface ChatLogContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun setScreenTitle(title: String)
        fun showMessage(chatMessage: ChatMessage)
        fun clearNewMessageEditText()
        fun scrollChatLogListToBottom()
    }

    interface Presenter {
        fun initView(view: ChatLogContract.View)
        fun destroyView()
        fun setSelectedUser(user: User)
        fun sendMessage(message: String)
    }
}