package com.example.groupizer.pojo.remote.dashboard

import android.util.Log
import com.example.groupizer.pojo.model.ad.Ad
import com.example.groupizer.pojo.model.ad.AdResponse
import com.example.groupizer.pojo.model.group.GroupApply
import com.example.groupizer.pojo.model.group.GroupRequest
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.pojo.remote.ApiBuilder
import retrofit2.await

class DashboardApiCall {
    private fun getService(): DashboardEndPoint {
        return ApiBuilder.buildService(DashboardEndPoint::class.java)
    }

    suspend fun getAds(auth_token: String):List<AdResponse> {
        return getService().getAds(auth_token).await()
    }

    suspend fun createAd(auth_token: String, ad: Ad): AdResponse {
        return getService().createAd(auth_token, ad).await()
    }

    suspend fun getUserGroups(auth_token: String): List<GroupResponse> {
        return getService().getUserGroups(auth_token).await()
    }

    suspend fun getGroupById(auth_token: String, groupRequest: GroupRequest): GroupResponse {
        return getService().getGroupById(auth_token, groupRequest.groupId).await()
    }

    suspend fun applyToGroup(auth_token: String, groupApply: GroupApply): Membership {
        Log.d(TAG, "applyToGroup: ${auth_token.substring(6)}, id: ${groupApply.group}")
        return getService().applyToGroup(auth_token, groupApply).await()
    }
    companion object {
        private const val TAG = "DashboardApiCall"
    }
}