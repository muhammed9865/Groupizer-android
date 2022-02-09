package com.example.groupizer.pojo.remote.dashboard

import com.example.groupizer.pojo.model.ad.Ad
import com.example.groupizer.pojo.model.ad.AdResponse
import com.example.groupizer.pojo.model.chat.group.messages.ChatMessages
import com.example.groupizer.pojo.model.chat.group.messages.Message
import com.example.groupizer.pojo.model.group.GroupApply
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.pojo.model.group.Membership
import retrofit2.Call
import retrofit2.http.*

interface DashboardEndPoint {
    @GET("/ads/")
    fun getAds(
        @Header("Authorization") auth_token: String
    ): Call<List<AdResponse>>

    @POST("/ads/")
    fun createAd(
        @Header("Authorization") auth_token: String,
        @Body ad: Ad
    ): Call<AdResponse>

    @GET("/groups/")
    fun getUserGroups(
        @Header("Authorization") auth_token: String
    ): Call<List<GroupResponse>>

    @GET("/groups/{id}/")
    fun getGroupById(
        @Header("Authorization") auth_token: String,
        @Path("id") groupId: Int
    ): Call<GroupResponse>

    @POST("/memberships/")
    fun applyToGroup(
        @Header("Authorization") auth_token: String,
        @Body groupApply: GroupApply
    ): Call<Membership>

    @PUT("/memberships/{id}/")
    fun changeMemberRank(
        @Header("Authorization") auth_token: String,
        @Path("id") memberId: Int,
        @Body membership: Membership
    ): Call<Membership>


    @GET("/chats/{chat_id}")
    fun getGroupMessages(
        @Header("Authorization") auth_token: String,
        @Path("chat_id") chat_id: Int
    ): Call<ChatMessages>
}