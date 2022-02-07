package com.example.groupizer.ui.dashboard.explore.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.AdItemBinding
import com.example.groupizer.pojo.model.ad.AdResponse

class AdsViewHolder(private val binding: AdItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(ad: AdResponse, viewAd: ViewAd?) = with(binding) {
        adTitle.text = ad.title
        groupOwnerName.text = ad.user.name
        adOwnerEmail.text = ad.user.email

        adViewBtn.setOnClickListener {
            viewAd?.onViewAdClicked(ad)
        }

    }
}