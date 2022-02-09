package com.example.groupizer.ui.group.members

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.pojo.repository.DashboardRepository
import com.example.groupizer.ui.dashboard.viewmodel.GroupsViewModelFactory
import com.example.groupizer.ui.getID
import com.example.groupizer.ui.getToken
import com.example.groupizer.ui.group.GroupActivity
import com.example.groupizer.ui.group.members.adapters.MembersAdapter
import com.example.groupizer.ui.group.members.adapters.NewRank
import com.example.groupizer.ui.group.viewmodel.GroupViewModel
import com.example.groupizer.ui.group.viewmodel.MembersGroupFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException


class Members : Fragment() {

    private val viewModel: GroupViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        prepareAdapter()
        return inflater.inflate(R.layout.fragment_members, container, false)
    }
    private fun prepareAdapter() {
        val adapter= MembersAdapter()
        adapter.setNewRank(object : NewRank{
            override fun onRankChanged(membership: Membership) {
                CoroutineScope(Dispatchers.Main).launch {
                    val memberId = membership.id
                    viewModel.changeMemberRank(getToken()!!, memberId, membership)
                    viewModel.updateGroup(getToken()!!, viewModel.getGroupId()!!).let {
                        viewModel.setGroup(it)
                        getMembers(adapter)
                    }
                }
            }
        })

        getMembers(adapter)

    }

    private fun getMembers(adapter: MembersAdapter) {
        viewModel.getMembers(getID()).observe(this) {
            adapter.submitList(it)
            prepareMembersList(adapter)
        }
    }

    private fun prepareMembersList(iAdapter: MembersAdapter) {
        val list = requireActivity().findViewById<RecyclerView>(R.id.group_activity_members_rv)

        list.apply {
            try {
                adapter = iAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }catch (e: NullPointerException) {
                requireActivity().finish()
            }

        }
    }








}