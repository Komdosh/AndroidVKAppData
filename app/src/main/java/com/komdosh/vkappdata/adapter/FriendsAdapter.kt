package com.komdosh.vkappdata.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import com.komdosh.vkappdata.model.User

/**
 * Created by komdosh on 09.03.17.
 */
class FriendsAdapter(val items: List<User>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        return ViewHolder(TextView(parent.context))
    }
    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        holder.textView.text = items[position].toString()
    }
    override fun getItemCount(): Int = items.size
    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}