package com.example.groupizer.ui.group.chat

import com.example.groupizer.pojo.model.chat.group.messages.Message

interface MessageReceive {
    fun onMessageReceived(message: Message)
}