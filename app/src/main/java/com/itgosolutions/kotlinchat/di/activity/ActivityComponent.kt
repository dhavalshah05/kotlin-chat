package com.itgosolutions.kotlinchat.di.activity

import com.itgosolutions.kotlinchat.ui.register.RegisterActivity
import dagger.Subcomponent

@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(registerActivity: RegisterActivity)
}