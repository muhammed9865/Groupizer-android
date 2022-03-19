package com.example.groupizer.ui.group.members

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupizer.databinding.FragmentMembersBinding
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.ui.getID
import com.example.groupizer.ui.getToken
import com.example.groupizer.ui.group.members.adapters.members.MembersAdapter
import com.example.groupizer.ui.group.members.adapters.members.NewRank
import com.example.groupizer.ui.group.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Members : Fragment() {

    private val activityViewModel: GroupViewModel by activityViewModels()
    private lateinit var binding: FragmentMembersBinding
    private val mAdapter: MembersAdapter by lazy {
        MembersAdapter(requireContext(), isUserAdmin())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMembersBinding.inflate(inflater, container, false)
        binding.groupActivityMembersRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        activityViewModel.getMembers(getID())
        prepareUserDetails()
        prepareAdapter()
        observeMembers()

        return binding.root
    }

    private fun prepareAdapter() {
        mAdapter.setNewRank(object : NewRank {
            override fun onRankChanged(membership: Membership) {
                activityViewModel.changeMemberRank(getToken()!!, membership, getID())
            }
        })

    }

    private fun observeMembers() {
        activityViewModel.members.observe(viewLifecycleOwner) {
            Log.d(TAG, "observeMembers: $it")
            mAdapter.submitList(it)
        }
    }


    private fun prepareUserDetails() {
        activityViewModel.getUser(getID()).let { membership ->
            membership?.user?.let {
                binding.apply {
                    groupUserName.text = it.name
                    groupUserRole.text = membership.role
                }
            }
        }
    }

    private fun isUserAdmin(): Boolean {
        return activityViewModel.getUser(getID())?.role == Roles.ADMIN
    }

    companion object {
        private const val TAG = "Members"
    }

}