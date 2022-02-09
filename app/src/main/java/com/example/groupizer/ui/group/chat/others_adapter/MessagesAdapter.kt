package com.example.groupizer.ui.dashboard.home.others_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.GroupItemBinding
import com.example.groupizer.databinding.MessageItemBinding
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.pojo.model.group.GroupResponse

class MessagesAdapter():ListAdapter<Message, MessagesViewHolder>(MessageCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.layoutParams = RecyclerView.LayoutParams((parent as RecyclerView).layoutManager!!.width,
            RecyclerView.LayoutParams.WRAP_CONTENT)
        return MessagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}