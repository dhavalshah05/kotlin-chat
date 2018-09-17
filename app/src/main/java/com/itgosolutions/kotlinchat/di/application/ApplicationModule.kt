package com.itgosolutions.kotlinchat.di.application

import android.app.Application
import com.itgosolutions.kotlinchat.manager.UserManager
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

    @Singleton
    @Provides
    fun providesUserManager(): UserManager {
        return UserManager()
    }
}