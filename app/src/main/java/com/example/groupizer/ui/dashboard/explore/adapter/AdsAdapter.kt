package com.example.groupizer.ui.dashboard.explore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.AdItemBinding
import com.example.groupizer.pojo.model.ad.AdResponse

class AdsAdapter:ListAdapter<AdResponse, AdsViewHolder>(AdsCallback()) {
    private var viewAd: ViewAd? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        val binding = AdItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.layoutParams = RecyclerView.LayoutParams((parent as RecyclerView).layoutManager!!.width,
            RecyclerView.LayoutParams.WRAP_CONTENT)
        return AdsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdsViewHolder, position: Int) {
        holder.bind(getItem(position), viewAd)
    }

    fun setViewAd(viewAd: ViewAd) {
        this.viewAd = viewAd
    }
}