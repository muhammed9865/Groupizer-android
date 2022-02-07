package com.example.groupizer.ui.dashboard.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.ad.Ad
import com.example.groupizer.pojo.model.ad.AdResponse
import com.example.groupizer.pojo.model.group.GroupApply
import com.example.groupizer.pojo.model.group.GroupRequest
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.pojo.repository.DashboardRepository

class SharedGroupsViewModel(private val repository: DashboardRepository): ViewModel() {

    private val _token = MutableLiveData<String>()
    private val _id = MutableLiveData<Int>()

    // ad title and ad description are used to create a new ad
    private val _adTitle = MutableLiveData<String>()
    private val _adDescription = MutableLiveData<String>()

    private val _ad = MutableLiveData<AdResponse>()

    fun setToken(auth_token: String) {
        _token.value = auth_token
    }

    fun setId(id: Int) {
        _id.value = id
    }

    fun getId() = _id.value

    fun setAdTitle(title: String) {
        _adTitle.value = title
    }

    fun setAdDescription(desc: String) {
        _adDescription.value = desc
    }

    fun setAd(ad: AdResponse) {
        _ad.value = ad
    }
    fun getAd() = _ad.value

    fun clearGroupsList(list: List<GroupResponse>): List<GroupResponse> {
        val newList: ArrayList<GroupResponse> = arrayListOf()
        for (i in list) {
            for (j in i.membership) {
                Log.d(TAG, "clearGroupsList: ${j.user}")
                j.user?.id?.let {
                    if (it == _id.value) {
                    if (j.role == Roles.ADMIN || j.role == Roles.MEMBER){
                        newList.add(i)
                    }
                }
                }

            }
        }
        return newList

    }

    fun clearOthersList(list: List<GroupResponse>): List<GroupResponse> {
        val newList: ArrayList<GroupResponse> = arrayListOf()
        for (i in list) {
            for (j in i.membership) {
                if (j.user?.id == getId()) {
                    if (j.role != Roles.ADMIN || j.role != Roles.MEMBER){
                        newList.add(i)
                    }
                }
            }
        }
        return newList

    }

    fun clearAdsList(list: List<AdResponse>): List<AdResponse> {
        val newList = ArrayList<AdResponse>(arrayListOf())
        for (i in list){
            if (i.user.id == getId()) {
                continue
            }
            var isMember= false
            for (j in i.group.membership) {
                isMember = _id.value == j.user?.id
            }

            if (!isMember)
                newList.add(i)
        }

        return newList
    }



    suspend fun getAllGroups() = repository.getUserGroups(_token.value!!)

    suspend fun getAllAds() = repository.getAds(_token.value!!)

    suspend fun getGroupById(groupRequest: GroupRequest) = repository.getGroupById(_token.value!!, groupRequest)

    suspend fun createAd() = repository.createAd(_token.value!!, Ad(_adTitle.value!!, _adDescription.value!!))

    suspend fun apply(groupApply: GroupApply) = repository.applyToGroup(_token.value!!, groupApply)

    companion object {
        private const val TAG = "SharedGroupsViewModel"
    }


}