package com.itgosolutions.kotlinchat.model

data class LatestMessage(val key: String,
                         val chatMessage: ChatMessage,
                         val username: String,
                         val profileUrl: String)