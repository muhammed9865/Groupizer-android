package com.example.groupizer.ui.dashboard.home.others_adapter

import android.content.Context
import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.databinding.GroupItemBinding
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.group.GroupResponse

class OthersViewHolder(private val binding: GroupItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context,item: GroupResponse, userId: Int) {
        binding.groupName.text = item.title
        for (member in item.membership) {
            if (member.user?.id == userId) {
                if (member.role == Roles.PENDING) {
                    binding.groupStatus.apply {
                        text = context.getString(R.string.pending)
                        setTextColor(context.getColor(R.color.yellow))
                    }
                }else if (member.role == Roles.REJECTED) {
                    binding.groupStatus.apply {
                        text = context.getString(R.string.rejected)
                        setTextColor(context.getColor(R.color.rejected_color))
                    }
                }
            }
        }
   }
}