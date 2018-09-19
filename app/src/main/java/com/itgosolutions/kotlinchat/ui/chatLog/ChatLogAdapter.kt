package com.itgosolutions.kotlinchat.ui.chatLog

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.model.ChatMessage
import com.itgosolutions.kotlinchat.model.MessageType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_message_from.view.*
import kotlinx.android.synthetic.main.row_message_to.view.*


class ChatLogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MESSAGE_FROM_VIEW_TYPE = 1
        const val MESSAGE_TO_VIEW_TYPE = 2
    }

    private val messages = mutableListOf<ChatMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return if (viewType == MESSAGE_FROM_VIEW_TYPE) {
            view = LayoutInflater.from(parent.context).inflate(R.layout.row_message_from, parent, false)
            MessageFromViewHolder(view)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.row_message_to, parent, false)
            MessageToViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].type == MessageType.FROM)
            MESSAGE_FROM_VIEW_TYPE
        else
            MESSAGE_TO_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MessageFromViewHolder -> holder.bind(messages[position])
            is MessageToViewHolder -> holder.bind(messages[position])
        }
    }

    fun showMessage(chatMessage: ChatMessage) {
        messages.add(chatMessage)
        notifyItemInserted(messages.size - 1)
    }

    class MessageFromViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(chatMessage: ChatMessage) {
            itemView.messageFromTextView.text = chatMessage.message

            if (!TextUtils.isEmpty(chatMessage.profileUrl.trim()))
                Picasso.get().load(chatMessage.profileUrl).into(itemView.avatarFromImageView)
        }
    }

    class MessageToViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(chatMessage: ChatMessage) {
            itemView.messageToTextView.text = chatMessage.message

            if (!TextUtils.isEmpty(chatMessage.profileUrl.trim()))
                Picasso.get().load(chatMessage.profileUrl).into(itemView.avatarToImageView)
        }
    }
}