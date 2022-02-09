package com.example.groupizer.pojo.repository

import com.example.groupizer.pojo.model.ad.Ad
import com.example.groupizer.pojo.model.ad.AdResponse
import com.example.groupizer.pojo.model.group.GroupApply
import com.example.groupizer.pojo.model.group.GroupRequest
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.pojo.remote.auth.AuthApiCall
import com.example.groupizer.pojo.remote.dashboard.DashboardApiCall
import retrofit2.await

class DashboardRepository(private val api: DashboardApiCall) {
    suspend fun getAds(auth_token: String) = api.getAds(auth_token)

    suspend fun createAd(auth_token: String, ad: Ad) = api.createAd(auth_token, ad)

    suspend fun getUserGroups(auth_token: String) = api.getUserGroups(auth_token)

    suspend fun getGroupById(auth_token: String, groupRequest: GroupRequest) =
        api.getGroupById(auth_token, groupRequest)

    suspend fun applyToGroup(auth_token: String, groupApply: GroupApply) =
        api.applyToGroup(auth_token, groupApply)

    suspend fun changeMemberRank(auth_token: String, memberId: Int, membership: Membership) =
        api.changeMemberRank(auth_token, memberId, membership)

    suspend fun getGroupMessages(auth_token: String, chat_id: Int) = api.getGroupMessages(auth_token, chat_id)


    companion object {
        private val lock = Any()
        private var instance: DashboardRepository? = null
        fun getInstance(): DashboardRepository {
            return instance ?: synchronized(lock) {
                instance ?: DashboardRepository(DashboardApiCall()).also { instance = it }
            }
        }
    }
}