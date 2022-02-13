package com.example.groupizer.ui.group.chat.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.ListItemMessageReceivedBinding
import com.example.groupizer.pojo.model.chat.group.messages.Message

class ReceivedMessagesHolder(private val binding: ListItemMessageReceivedBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(message: Message) {
        binding.apply {
            messageText.text = message.text
            messageDate.text = message.sent
            messageUser.text = message.user?.name ?: "User"
        }
    }
}