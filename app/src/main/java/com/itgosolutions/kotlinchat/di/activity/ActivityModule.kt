package com.itgosolutions.kotlinchat.di.activity

import android.content.Context
import android.support.v4.app.FragmentActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val _activity: FragmentActivity) {

    @Provides
    fun provideContext(): Context {
        return _activity;
    }

    @Provides
    fun provideString(): String {
        return "Hello Dhaval Shah"
    }
}