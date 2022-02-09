package com.example.groupizer.pojo.model.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SendMessage(
    @Json(name = "message")
    val message: String
)
