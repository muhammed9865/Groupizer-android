package com.example.groupizer.pojo.model.ad

import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.group.GroupResponse
import com.google.gson.annotations.SerializedName

// TODO Display it in the Explore fragment
data class AdResponse(
    val description: String,
    val group: GroupResponse,
    val id: Int,
    val title: String,
    val user: AuthResponse
)