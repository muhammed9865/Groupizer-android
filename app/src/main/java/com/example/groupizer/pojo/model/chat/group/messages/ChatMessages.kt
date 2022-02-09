package com.example.groupizer.pojo.model.chat.group.messages


data class ChatMessages(
    val group: Int, // 9
    val id: Int, // 3
    val messages: List<Message>,
    val title: String // Test my name
)