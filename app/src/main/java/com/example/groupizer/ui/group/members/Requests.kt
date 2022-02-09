package com.example.groupizer.ui.group.members

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.databinding.FragmentRequestsBinding
import com.example.groupizer.pojo.model.group.Membership
import com.example.groupizer.ui.getID
import com.example.groupizer.ui.getToken
import com.example.groupizer.ui.group.members.adapters.MembersAdapter
import com.example.groupizer.ui.group.members.adapters.NewRank
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
        val list = binding.groupActivityMembersRequestsRv

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