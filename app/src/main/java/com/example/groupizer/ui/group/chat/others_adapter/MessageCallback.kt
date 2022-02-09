package com.example.groupizer.ui.dashboard.home.others_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.pojo.model.group.GroupResponse

class MessageCallback:DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}