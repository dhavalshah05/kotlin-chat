package com.itgosolutions.kotlinchat.manager

import com.itgosolutions.kotlinchat.model.User

class UserManager {
    var loggedInUser: User? = null

    val isLoggedIn: Boolean
        get() = loggedInUser != null

    fun logoutUser() {
        loggedInUser = null
    }
}