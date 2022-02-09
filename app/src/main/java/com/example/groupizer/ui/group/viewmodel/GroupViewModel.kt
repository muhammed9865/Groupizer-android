package com.example.groupizer.ui.group.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.group.GroupRequest
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.pojo.repository.DashboardRepository

class GroupViewModel(private val repository: DashboardRepository) : ViewModel() {
    private val TAG = "GroupViewModel"
    private val _group = MutableLiveData<GroupResponse>()
    private val groups: LiveData<GroupResponse> = _group
    private val _members = MutableLiveData<List<Membership>>()
    private val members: LiveData<List<Membership>> = _members
    private val groupId = MutableLiveData<Int>()

    fun setGroup(group: GroupResponse) {
        Log.d(TAG, "test setGroup: ${group.toString()}")
        _group.value = group
        groupId.value = group.id

    }

    fun getGroup() = groups

    fun getGroupId() = groupId.value

    fun getUser(id: Int): Membership? {
        var user: Membership? = null
        for (i in groups.value!!.membership) {
            if (i.user?.id == id) {
                user = i
                break
            }
        }
        return user
    }

    fun getMembers(id: Int): LiveData<List<Membership>> {
        val newList = ArrayList<Membership>()

        for (i in _group.value!!.membership) {
            if (i.role == Roles.ADMIN || i.role == Roles.MEMBER || i.role == Roles.PENDING) {
                if (i.id == id) {
                    continue
                } else
                    newList.add(i)
            }
        }
        _members.value = newList
        return members
    }

    suspend fun updateGroup(auth_token: String, groupId: Int) =
        repository.getGroupById(auth_token, GroupRequest(groupId))

    suspend fun changeMemberRank(auth_token: String, memberId: Int, membership: Membership) =
        repository.changeMemberRank(auth_token, memberId, membership)
}