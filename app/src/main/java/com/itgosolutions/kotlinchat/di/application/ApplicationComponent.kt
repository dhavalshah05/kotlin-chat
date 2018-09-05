package com.itgosolutions.kotlinchat.di.application

import com.itgosolutions.kotlinchat.di.activity.ActivityComponent
import com.itgosolutions.kotlinchat.di.activity.ActivityModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent
}