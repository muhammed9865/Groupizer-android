package com.example.groupizer.ui.group.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.groupizer.R
import com.example.groupizer.databinding.FragmentGroupMembersBinding
import com.example.groupizer.ui.getID
import com.example.groupizer.ui.group.GroupActivity
import com.example.groupizer.ui.group.viewmodel.GroupViewModel
import com.google.android.material.tabs.TabLayoutMediator

class GroupMembers : Fragment() {
    private lateinit var binding: FragmentGroupMembersBinding
    private lateinit var pager: ViewPager2
    private val viewModel: GroupViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupMembersBinding.inflate(inflater, container, false)
        pager = binding.membersPager

        pager.adapter = MembersFragmentsAdapter(this)
        buildUI()


        return binding.root
    }

    private fun buildUI() {
        viewModel.getUser(getID()).let {
            it?.let { user ->
                binding.groupUserName.text = user.user?.name
                binding.groupUserRole.text = user.role
            }
        }
        val texts = listOf("Members", "Requests")
        TabLayoutMediator(binding.membersTabsLayout, pager) { tab, position ->
            tab.text = texts[position]
        }.attach()
    }


}