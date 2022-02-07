package com.example.groupizer.ui.dashboard.explore.adapter

import com.example.groupizer.pojo.model.ad.AdResponse

interface ViewAd {
    fun onViewAdClicked(ad: AdResponse)
}