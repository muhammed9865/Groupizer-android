package com.example.groupizer.ui.dashboard.home.others_adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.groupizer.pojo.model.group.GroupResponse

class OthersCallback:DiffUtil.ItemCallback<GroupResponse>() {
    override fun areItemsTheSame(oldItem: GroupResponse, newItem: GroupResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GroupResponse, newItem: GroupResponse): Boolean {
        return oldItem == newItem
    }
}