package com.example.groupizer.ui.dashboard.home.others_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.groupizer.databinding.GroupItemBinding
import com.example.groupizer.pojo.model.group.GroupResponse

class OthersAdapter(private val context: Context,private val userId: Int):ListAdapter<GroupResponse, OthersViewHolder>(OthersCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OthersViewHolder {
        val binding = GroupItemBinding.inflate(LayoutInflater.from(parent.context))
        return OthersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OthersViewHolder, position: Int) {
        holder.bind(context, getItem(position), userId)
    }
}