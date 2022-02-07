package com.example.groupizer.pojo.model.group

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// TODO Receive it as a response from @GET /groups/
// TODO Or as a response from @POST /groups/<id>/ id is sent by GroupRequest
data class GroupResponse(
    val id: Int,
    val ad: Int,
    @SerializedName("memberships")
    var membership: ArrayList<Membership>,
    val title: String
)