package com.itgosolutions.kotlinchat.model

class User() {

    lateinit var uid: String
    lateinit var username: String
    lateinit var email: String
    var profileUrl: String = ""

    constructor(uid: String, username: String, email: String, profileUrl: String = "") : this() {
        this.uid = uid
        this.username = username
        this.email = email
        this.profileUrl = profileUrl
    }

}