package com.example.groupizer.ui.group.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.groupizer.pojo.repository.DashboardRepository

@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory(private val service: DashboardRepository, private val token: String, private val chat_id: Int): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(service, token, chat_id) as T
    }
}