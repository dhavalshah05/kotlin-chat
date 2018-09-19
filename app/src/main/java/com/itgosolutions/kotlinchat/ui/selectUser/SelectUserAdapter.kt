package com.itgosolutions.kotlinchat.ui.selectUser

import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.itgosolutions.kotlinchat.R
import com.itgosolutions.kotlinchat.model.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_select_user.view.*

class SelectUserAdapter(private val users: List<User>) : RecyclerView.Adapter<SelectUserAdapter.SelectUserViewHolder>() {

    private lateinit var listener: (item: User) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_select_user, parent, false)
        return SelectUserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: SelectUserViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {
            if (::listener.isInitialized)
                listener(users[position])
        }
    }

    fun setOnItemClickListener(listener: (item: User) -> Unit) {
        this.listener = listener
    }

    class SelectUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(user: User) {
            if (!TextUtils.isEmpty(user.profileUrl))
                Picasso.get().load(user.profileUrl).into(itemView.avatarImageView)

            itemView.usernameTextView.text = user.username
        }
    }
}
