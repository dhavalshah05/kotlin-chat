package com.itgosolutions.kotlinchat.di.activity

import com.itgosolutions.kotlinchat.ui.home.HomeActivity
import com.itgosolutions.kotlinchat.ui.login.LoginActivity
import com.itgosolutions.kotlinchat.ui.register.RegisterActivity
import dagger.Subcomponent

@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(registerActivity: RegisterActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(homeActivity: HomeActivity)
}