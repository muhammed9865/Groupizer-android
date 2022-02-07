package com.example.groupizer.ui.group.members.adapters

import com.example.groupizer.pojo.model.group.Membership

interface NewRank {
    fun onRankChanged(membership: Membership)
}