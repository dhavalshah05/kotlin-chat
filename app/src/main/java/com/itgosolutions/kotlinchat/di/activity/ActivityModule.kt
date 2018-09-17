package com.itgosolutions.kotlinchat.di.activity

import android.content.Context
import android.support.v4.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.itgosolutions.kotlinchat.manager.UserManager
import com.itgosolutions.kotlinchat.ui.home.HomeContract
import com.itgosolutions.kotlinchat.ui.home.HomePresenter
import com.itgosolutions.kotlinchat.ui.login.LoginContract
import com.itgosolutions.kotlinchat.ui.login.LoginPresenter
import com.itgosolutions.kotlinchat.ui.register.RegisterContract
import com.itgosolutions.kotlinchat.ui.register.RegisterPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val _activity: FragmentActivity) {

    @Provides
    fun providesContext(): Context {
        return _activity
    }

    @Provides
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun providesFirebaseDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    fun providesFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun providesRegisterPresenter(auth: FirebaseAuth,
                                  database: FirebaseDatabase,
                                  storage: FirebaseStorage,
                                  userManager: UserManager): RegisterContract.Presenter {
        return RegisterPresenter(auth, database, storage, userManager)
    }

    @Provides
    fun providesHomePresenter(auth: FirebaseAuth, database: FirebaseDatabase, userManager: UserManager): HomeContract.Presenter {
        return HomePresenter(auth, database, userManager)
    }

    @Provides
    fun providesLoginPresenter(auth: FirebaseAuth, database: FirebaseDatabase, userManager: UserManager): LoginContract.Presenter {
        return LoginPresenter(auth, database, userManager)
    }
}