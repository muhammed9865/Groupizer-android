package com.example.groupizer.ui.group.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.ListItemMessageReceivedBinding
import com.example.groupizer.databinding.ListItemMessageSentBinding
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.ui.dashboard.home.others_adapter.MessageCallback


class MessagesAdapter(private val user_Id: Int):ListAdapter<Message, RecyclerView.ViewHolder>(
    MessageCallback()
) {
    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).user?.id == user_Id) {
            VIEW_TYPE_MESSAGE_SENT
        }else {
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MESSAGE_SENT){
            val binding = ListItemMessageSentBinding.inflate(LayoutInflater.from(parent.context))
            binding.root.layoutParams = RecyclerView.LayoutParams((parent as RecyclerView).layoutManager!!.width,
                RecyclerView.LayoutParams.WRAP_CONTENT)
             SentMessagesHolder(binding)
        }else {
            val binding = ListItemMessageReceivedBinding.inflate(LayoutInflater.from(parent.context))
            binding.root.layoutParams = RecyclerView.LayoutParams((parent as RecyclerView).layoutManager!!.width,
                RecyclerView.LayoutParams.WRAP_CONTENT)
            ReceivedMessagesHolder(binding)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> {
                (holder as SentMessagesHolder).bind(getItem(position), user_Id)
            }
            VIEW_TYPE_MESSAGE_RECEIVED -> {
                (holder as ReceivedMessagesHolder).bind(getItem(position))
            }
        }
    }

    companion object {
        private const val VIEW_TYPE_MESSAGE_SENT = 1
        private const val VIEW_TYPE_MESSAGE_RECEIVED = 2
    }
}