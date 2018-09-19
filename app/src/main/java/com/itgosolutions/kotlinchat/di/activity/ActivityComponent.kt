package com.itgosolutions.kotlinchat.di.activity

import com.itgosolutions.kotlinchat.ui.chatLog.ChatLogActivity
import com.itgosolutions.kotlinchat.ui.home.HomeActivity
import com.itgosolutions.kotlinchat.ui.login.LoginActivity
import com.itgosolutions.kotlinchat.ui.register.RegisterActivity
import com.itgosolutions.kotlinchat.ui.selectUser.SelectUserActivity
import dagger.Subcomponent

@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(registerActivity: RegisterActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(selectUserActivity: SelectUserActivity)
    fun inject(chatLogActivity: ChatLogActivity)
}