package com.example.groupizer.ui.dashboard.home.groups_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.databinding.GroupItemBinding
import com.example.groupizer.databinding.MyGroupItemBinding
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.ui.dashboard.home.others_adapter.OthersCallback
import com.example.groupizer.ui.dashboard.home.others_adapter.OthersViewHolder

class GroupsAdapter(private val context: Context, private val userId: Int):ListAdapter<GroupResponse, GroupsViewHolder>(
    GroupsCallback()
) {
    private var startChat: StartChat? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsViewHolder {
        val binding = MyGroupItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.layoutParams = RecyclerView.LayoutParams((parent as RecyclerView).layoutManager!!.width,
        RecyclerView.LayoutParams.WRAP_CONTENT)
        return GroupsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupsViewHolder, position: Int) {
        holder.bind(context,getItem(position), userId, startChat)
    }

    fun setStartChat(startChat: StartChat) {
        this.startChat = startChat
    }


}