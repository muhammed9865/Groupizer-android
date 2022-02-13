package com.example.groupizer.ui.group.members.adapters.requests

import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.databinding.RequestItemBinding
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.ui.group.members.adapters.members.NewRank

class RequestsViewHolder(private val binding: RequestItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Membership, newRank: NewRank?) {
            binding.apply {
                acceptMemberBtn.setOnClickListener {
                    item.role = Roles.MEMBER
                    newRank?.onRankChanged(item)
                }

                rejectMemberBtn.setOnClickListener {
                    item.role = Roles.REJECTED
                    newRank?.onRankChanged(item)
                }

                groupRequestItemEmail.text = item.user?.email
                groupRequestUserName.text = item.user?.name
            }
        }
}