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
import com.example.groupizer.ui.group.members.adapters.members.NewRank
import com.example.groupizer.ui.group.members.adapters.requests.RequestsAdapter
import com.example.groupizer.ui.group.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Requests : Fragment() {
    private val viewModel: GroupViewModel by activityViewModels()
    private lateinit var binding: FragmentRequestsBinding
    private val mAdapter: RequestsAdapter by lazy {
        RequestsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRequestsBinding.inflate(inflater, container, false)
        // Attach the adapter to the list
        binding.groupActivityMembersRequestsRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        onMemberRankChanged()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun onMemberRankChanged() {
        mAdapter.setMemberResult(object : NewRank {
            override fun onRankChanged(membership: Membership) {
                CoroutineScope(Dispatchers.Main).launch {
                    val memberId = membership.id
                    viewModel.changeMemberRank(getToken()!!, memberId, membership)
                    viewModel.updateGroup(getToken()!!, viewModel.getGroupId()!!).let {
                        viewModel.setGroup(it)
                        getMembers()
                    }
                }
            }
        })


    }

    private fun getMembers() {
        viewModel.getRequests().observe(viewLifecycleOwner) { members ->
            if (isUserAuthorized()) {
                binding.notAuthorized.visibility = View.GONE
                binding.emptyRequestsList.visibility =
                    if (members.isEmpty()) View.VISIBLE else View.GONE
                mAdapter.submitList(members)
                mAdapter.submitList(members)
                binding.emptyRequestsList.visibility = View.VISIBLE

            } else {
                binding.emptyRequestsList.visibility = View.GONE
                binding.notAuthorized.visibility = View.VISIBLE
            }
        }
    }



    private fun isUserAuthorized(): Boolean {
        return viewModel.getUser(getID())?.role == Roles.ADMIN
    }

}