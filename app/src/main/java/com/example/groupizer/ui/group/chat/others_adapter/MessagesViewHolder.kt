package com.example.groupizer.ui.dashboard.home.others_adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.databinding.GroupItemBinding
import com.example.groupizer.databinding.MessageItemBinding
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.pojo.model.group.GroupResponse

class MessagesViewHolder(private val binding: MessageItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Message) {
        binding.apply {
            messageText.text = item.text
            messageUser.text = item.user?.name
        }
   }
}