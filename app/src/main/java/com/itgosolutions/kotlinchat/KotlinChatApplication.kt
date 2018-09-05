package com.itgosolutions.kotlinchat

import android.app.Application
import com.itgosolutions.kotlinchat.di.application.ApplicationComponent
import com.itgosolutions.kotlinchat.di.application.ApplicationModule
import com.itgosolutions.kotlinchat.di.application.DaggerApplicationComponent

class KotlinChatApplication: Application() {

    companion object {
        private lateinit var applicationComponent: ApplicationComponent

        fun getApplicationComponent(): ApplicationComponent {
            return applicationComponent;
        }
    }



    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build();
    }

}