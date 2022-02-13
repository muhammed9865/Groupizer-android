package com.example.groupizer.ui.group.members.adapters.requests

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.RequestItemBinding
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.ui.group.members.adapters.members.MembersCallback
import com.example.groupizer.ui.group.members.adapters.members.NewRank


class RequestsAdapter: ListAdapter<Membership, RequestsViewHolder>(MembersCallback()) {
    private var newRank: NewRank? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsViewHolder {
        val binding = RequestItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.root.layoutParams = RecyclerView.LayoutParams((parent as RecyclerView).layoutManager!!.width,
            RecyclerView.LayoutParams.WRAP_CONTENT)
        return RequestsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int) {
        holder.bind(getItem(position), newRank)
    }

    fun setMemberResult(newRank: NewRank) {
        this.newRank = newRank
    }
}