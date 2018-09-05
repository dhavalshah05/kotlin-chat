package com.itgosolutions.kotlinchat.di.application

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val _application: Application) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return _application;
    }

}