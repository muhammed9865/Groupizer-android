package com.example.groupizer.ui.group.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.group.GroupRequest
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.pojo.repository.DashboardRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GroupViewModel(private val repository: DashboardRepository) : ViewModel() {
    private val TAG = "GroupViewModel"
    private val _group = MutableLiveData<GroupResponse>()
    val groups: LiveData<GroupResponse> = _group
    private val _members = MutableLiveData<List<Membership>>()
    val members: LiveData<List<Membership>> = _members
    private val groupId = MutableLiveData<Int>()

    fun setGroup(group: GroupResponse) {
        Log.d(TAG, "test setGroup: ${group.toString()}")
        _group.value = group
        groupId.value = group.id

    }

    fun getChatId() = _group.value!!.chat

    private fun getGroupId() = groupId.value

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

    fun getMembers(id: Int) {
        viewModelScope.launch {
            val newList = ArrayList<Membership>()
            for (i in _group.value!!.membership) {
                if (i.role == Roles.ADMIN || i.role == Roles.MEMBER) {
                    if (i.id == id) {
                        continue
                    } else
                        newList.add(i)
                }
            }
            _members.value = newList
        }
    }

    fun getRequests() {
        viewModelScope.launch {
            val newList = ArrayList<Membership>()
            for (i in _group.value!!.membership) {
                if (i.role == Roles.PENDING) {
                    newList.add(i)
                }
            }
            _members.value = newList
        }
    }



    private fun updateGroup(auth_token: String, groupId: Int) {
        viewModelScope.launch {
            flow {
                emit(repository.getGroupById(auth_token, GroupRequest(groupId)))
            }.collect {
                _group.value = it
                setGroup(it)
            }
        }
    }

    fun answerPendingRequest(auth_token: String, memberId: Int, membership: Membership) {
        viewModelScope.launch {
            flow {
                emit(repository.changeMemberRank(auth_token, memberId, membership))
            }.collect {
                getGroupId()?.let { groupId -> updateGroup(auth_token, groupId) }
                getRequests()
            }
        }
    }

    fun changeMemberRank(auth_token: String, membership: Membership, userId: Int) {
        viewModelScope.launch {
            flow {
                emit(repository.changeMemberRank(auth_token, membership.id, membership))
            }.collect {
                getGroupId()?.let { groupId -> updateGroup(auth_token, groupId) }
                getMembers(userId)
            }
        }
    }
}