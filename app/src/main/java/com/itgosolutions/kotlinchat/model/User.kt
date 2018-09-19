package com.itgosolutions.kotlinchat.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(val uid: String, val username: String, val email: String, val profileUrl: String = "") : Parcelable {

    constructor() : this("", "", "")

}