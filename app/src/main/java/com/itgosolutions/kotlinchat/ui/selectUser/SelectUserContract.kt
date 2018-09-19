package com.itgosolutions.kotlinchat.ui.selectUser

import com.itgosolutions.kotlinchat.model.User

interface SelectUserContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showUsers(users: List<User>)
        fun hideUsers()
        fun showNoUserFoundMessage()
        fun hideNoUserFoundMessage()
    }

    interface Presenter {
        fun initView(view: SelectUserContract.View)
        fun destroyView()
    }
}