package com.example.groupizer.ui.dashboard.home.groups_adapter

import android.content.Context
import android.util.Log
import android.view.View
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.databinding.GroupItemBinding
import com.example.groupizer.databinding.MyGroupItemBinding
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.group.GroupResponse
import com.google.android.gms.dynamic.IFragmentWrapper

class GroupsViewHolder(private val binding: MyGroupItemBinding): RecyclerView.ViewHolder(binding.root) {
    private val TAG = "GroupsViewHolder"
    fun bind(context: Context, item: GroupResponse, userId: Int, startChat: StartChat?) {
        binding.apply {

            for (user in item.membership) {
                // Setting the user image
                if (user.role == Roles.ADMIN) {
                    if (user.user?.id == userId) {
                        binding.roleImage.setImageDrawable(context.getDrawable(R.drawable.admin))
                        groupOwner.text = context.getString(R.string.me)
                        break
                    }
                    else {
                        binding.roleImage.setImageDrawable(context.getDrawable(R.drawable.member))
                        // Todo find a way to set the groupOwner name
                        groupOwner.text = user.user?.name
                        break
                    }
                }
            }

            membersCount.text = item.membership.size.toString()
            myGroupName.text = item.title
            startChat?.let { data ->
                chat.setOnClickListener {
                    data.onStartChat(item)
                }
            }

        }
    }


}