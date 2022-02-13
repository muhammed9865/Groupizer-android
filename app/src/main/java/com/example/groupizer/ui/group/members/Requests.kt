package com.example.groupizer.ui.group.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupizer.databinding.FragmentRequestsBinding
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.ui.getID
import com.example.groupizer.ui.getToken
import com.example.groupizer.ui.group.members.adapters.members.MembersAdapter
import com.example.groupizer.ui.group.members.adapters.members.NewRank
import com.example.groupizer.ui.group.members.adapters.requests.RequestsAdapter
import com.example.groupizer.ui.group.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.NullPointerException


class Requests : Fragment() {
    private val viewModel: GroupViewModel by activityViewModels()
    private lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsBinding.inflate(inflater, container, false)

        prepareAdapter()


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun prepareAdapter() {
        val adapter = RequestsAdapter()
        adapter.setMemberResult(object : NewRank {
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

    private fun getMembers(adapter: RequestsAdapter) {
        viewModel.getRequests().observe(this) {
            if (isUserAuthorized()) {
                binding.notAuthorized.visibility = View.GONE
                if (it.isNotEmpty()) {
                    binding.emptyRequestsList.visibility = View.GONE
                    adapter.submitList(it)
                    prepareMembersList(adapter)
                } else {
                    binding.emptyRequestsList.visibility = View.VISIBLE
                }
            } else {
                binding.emptyRequestsList.visibility = View.GONE
                binding.notAuthorized.visibility = View.VISIBLE
            }
        }
    }


    private fun prepareMembersList(iAdapter: RequestsAdapter) {
        val list = binding.groupActivityMembersRequestsRv

        list.apply {
            adapter = iAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }
    }

    private fun isUserAuthorized(): Boolean {
        return viewModel.getUser(getID())?.role == Roles.ADMIN
    }

}