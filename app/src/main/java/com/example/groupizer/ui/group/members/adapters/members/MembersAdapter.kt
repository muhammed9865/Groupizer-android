package com.example.groupizer.ui.group.members.adapters.members

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.GroupMemberItemBinding
import com.example.groupizer.pojo.model.group.Membership

class MembersAdapter(private val context: Context, private val isAdmin: Boolean): ListAdapter<Membership, MembersViewHolder>(MembersCallback()) {
    private var newRank: NewRank? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        val binding = GroupMemberItemBinding.inflate(LayoutInflater.from(context))
        binding.root.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
        RecyclerView.LayoutParams.WRAP_CONTENT)

        return MembersViewHolder(parent.context, binding)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        holder.bind(getItem(position), newRank, isAdmin)
    }

    fun setNewRank(newRank: NewRank) {
        this.newRank = newRank
    }
}