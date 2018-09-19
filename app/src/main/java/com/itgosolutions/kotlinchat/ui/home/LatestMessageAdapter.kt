package com.itgosolutions.kotlinchat.ui.home

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.model.LatestMessage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_latest_message.view.*

class LatestMessageAdapter : RecyclerView.Adapter<LatestMessageAdapter.LatestMessageViewHolder>() {

    private val _latestMessages = mutableListOf<LatestMessage>()
    private lateinit var listener: (selectedUserId: String) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_latest_message, parent, false)
        return LatestMessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _latestMessages.size
    }

    override fun onBindViewHolder(holder: LatestMessageViewHolder, position: Int) {
        holder.bind(_latestMessages[position])
        holder.itemView.setOnClickListener {
            if (::listener.isInitialized)
                listener(_latestMessages[position].key)
        }
    }

    fun setOnItemClickListener(listener: (selectedUserId: String) -> Unit) {
        this.listener = listener
    }

    fun addLatestMessage(latestMessage: LatestMessage, previousChildKey: String?) {
        var index = 0
        if (previousChildKey != null) {
            index = getIndexForKey(previousChildKey) + 1
        }
        _latestMessages.add(index, latestMessage)
        notifyItemInserted(index)
    }

    fun updateLatestMessage(latestMessage: LatestMessage) {
        val index = getIndexForKey(latestMessage.key)
        _latestMessages[index] = latestMessage
        notifyItemChanged(index)
    }

    fun clear() {
        _latestMessages.clear()
        notifyDataSetChanged()
    }

    private fun getIndexForKey(key: String): Int {
        var index = 0
        for (latestMessage in _latestMessages) {
            if (latestMessage.key == key) {
                return index
            } else {
                index++
            }
        }
        throw IllegalArgumentException("Key not found")
    }

    class LatestMessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(latestMessage: LatestMessage) {
            itemView.usernameTextView.text = latestMessage.username
            itemView.latestMessageTextView.text = latestMessage.chatMessage.message
            if (!TextUtils.isEmpty(latestMessage.profileUrl))
                Picasso.get().load(latestMessage.profileUrl).into(itemView.avatarImageView)
        }

    }
}