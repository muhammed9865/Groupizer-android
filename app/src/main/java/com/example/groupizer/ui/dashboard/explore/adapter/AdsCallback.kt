package com.example.groupizer.ui.dashboard.explore.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.groupizer.pojo.model.ad.AdResponse

class AdsCallback:DiffUtil.ItemCallback<AdResponse>() {
    override fun areItemsTheSame(oldItem: AdResponse, newItem: AdResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AdResponse, newItem: AdResponse): Boolean {
        return oldItem == newItem
    }
}