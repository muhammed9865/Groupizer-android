package com.example.groupizer.ui.dashboard.home.groups_adapter

import com.example.groupizer.pojo.model.group.GroupResponse

interface StartChat {
    fun onStartChat(group: GroupResponse)
}