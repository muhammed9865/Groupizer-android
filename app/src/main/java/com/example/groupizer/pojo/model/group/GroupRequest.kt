package com.example.groupizer.pojo.model.group

import com.google.gson.annotations.SerializedName

// TODO Use it with @GET /groups/<id>/
// @Receive Group Response with a specific group
data class GroupRequest(
    @SerializedName("id")
    val groupId: Int
)
