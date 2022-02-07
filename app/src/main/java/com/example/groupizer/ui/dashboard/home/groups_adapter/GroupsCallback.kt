package com.example.groupizer.ui.dashboard.home.groups_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.groupizer.pojo.model.group.GroupResponse

class GroupsCallback: DiffUtil.ItemCallback<GroupResponse>() {
    override fun areItemsTheSame(oldItem: GroupResponse, newItem: GroupResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GroupResponse, newItem: GroupResponse): Boolean {
        return oldItem == newItem
    }
}