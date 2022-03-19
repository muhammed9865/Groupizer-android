package com.example.groupizer.ui.group.members

import android.os.Bundle
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
import java.lang.NullPointerException


class Members : Fragment() {

    private val activityViewModel: GroupViewModel by activityViewModels()
    private lateinit var binding: FragmentMembersBinding
    private lateinit var adapter: MembersAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = MembersAdapter(requireContext(), isUserAdmin())
        binding = FragmentMembersBinding.inflate(inflater, container, false)
        prepareUserDetails()
        prepareAdapter()


        return binding.root
    }
    private fun prepareAdapter() {
        getMembers(adapter)

        adapter.setNewRank(object : NewRank {
            override fun onRankChanged(membership: Membership) {
                CoroutineScope(Dispatchers.Main).launch {
                    val memberId = membership.id
                    activityViewModel.changeMemberRank(getToken()!!, memberId, membership)
                    activityViewModel.updateGroup(getToken()!!, activityViewModel.getGroupId()!!).let {
                        activityViewModel.setGroup(it)
                        getMembers(adapter)
                    }
                }
            }
        })

    }

    private fun getMembers(adapter: MembersAdapter) {
        activityViewModel.getMembers(getID()).observe(viewLifecycleOwner) {
            adapter.submitList(it)
            prepareMembersList(adapter)
        }
    }

    private fun prepareMembersList(iAdapter: MembersAdapter) {
        val list = binding.groupActivityMembersRv

        list.apply {
            try {
                adapter = iAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }catch (e: NullPointerException) {
               /* requireActivity().finish()*/
            }

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


}