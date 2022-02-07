package com.example.groupizer.ui.group.members.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.groupizer.pojo.model.group.Membership

class MembersCallback: DiffUtil.ItemCallback<Membership>() {
    override fun areItemsTheSame(oldItem: Membership, newItem: Membership): Boolean {
        return oldItem.user == newItem.user
    }

    override fun areContentsTheSame(oldItem: Membership, newItem: Membership): Boolean {
        return oldItem == newItem
    }
}