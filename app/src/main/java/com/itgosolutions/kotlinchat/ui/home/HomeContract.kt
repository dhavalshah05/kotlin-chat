package com.itgosolutions.kotlinchat.ui.home

interface HomeContract {

    interface View {
        fun setUsername(username: String)
        fun startLoginScreen()
    }

    interface Presenter {
        fun initView(view: HomeContract.View)
        fun destroyView()
        fun logout()
    }
}