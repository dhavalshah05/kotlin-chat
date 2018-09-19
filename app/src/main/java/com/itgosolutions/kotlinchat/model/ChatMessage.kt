package com.itgosolutions.kotlinchat.model

import com.google.firebase.database.Exclude

data class ChatMessage(val id: String,
                       val message: String,
                       val fromId: String,
                       val toId: String,
                       val timestamp: Long = System.currentTimeMillis() / 1000,
                       @set:Exclude @get:Exclude var type: MessageType = MessageType.FROM,
                       @set:Exclude @get:Exclude var profileUrl: String = ""
) {
    constructor() : this("", "", "", "")
}