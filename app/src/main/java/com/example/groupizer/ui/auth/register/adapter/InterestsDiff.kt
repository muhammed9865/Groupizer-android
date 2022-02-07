package com.example.groupizer.ui.auth.register.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.groupizer.pojo.model.interest.InterestsResponse

class InterestsDiff:DiffUtil.ItemCallback<InterestsResponse>() {
    override fun areItemsTheSame(oldItem: InterestsResponse, newItem: InterestsResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: InterestsResponse,
        newItem: InterestsResponse
    ): Boolean {
        return oldItem == newItem
    }


}