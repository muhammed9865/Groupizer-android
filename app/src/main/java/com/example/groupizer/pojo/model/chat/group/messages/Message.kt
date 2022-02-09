package com.example.groupizer.pojo.model.chat.group.messages

import com.example.groupizer.pojo.model.auth.AuthResponse
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Message(
    val chat: Int = -1, // 3
    val id: Int = -1, // 1
    val sent: String = "", // 2022-02-08T17:12:42.667238Z
    val text: String = "", // zby suck it
    val user: AuthResponse? = null
)