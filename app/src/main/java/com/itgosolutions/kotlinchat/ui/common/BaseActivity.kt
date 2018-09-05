package com.itgosolutions.kotlinchat.ui.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.itgosolutions.kotlinchat.KotlinChatApplication
import com.itgosolutions.kotlinchat.di.activity.ActivityComponent
import com.itgosolutions.kotlinchat.di.activity.ActivityModule

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var _activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityComponent = KotlinChatApplication.getApplicationComponent().newActivityComponent(ActivityModule(this))
    }

    protected fun getActivityComponent(): ActivityComponent {
        return _activityComponent
    }

}