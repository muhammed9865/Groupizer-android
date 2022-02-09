package com.example.groupizer.ui.dashboard.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.groupizer.R
import com.example.groupizer.databinding.FragmentGroupsBinding
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.pojo.repository.DashboardRepository
import com.example.groupizer.ui.dashboard.DashboardActivity
import com.example.groupizer.ui.dashboard.home.groups_adapter.GroupsAdapter
import com.example.groupizer.ui.dashboard.home.groups_adapter.StartChat
import com.example.groupizer.ui.dashboard.viewmodel.GroupsViewModelFactory
import com.example.groupizer.ui.dashboard.viewmodel.SharedGroupsViewModel
import com.example.groupizer.ui.dashboard.utils.AdDialog
import com.example.groupizer.ui.getID
import com.example.groupizer.ui.getToken
import com.example.groupizer.ui.group.GroupActivity
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GroupsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    private val viewModel by activityViewModels<SharedGroupsViewModel> {
        GroupsViewModelFactory(DashboardRepository.getInstance())
    }
    private lateinit var binding: FragmentGroupsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroupsBinding.inflate(inflater, container, false)
        viewModel.setToken(requireActivity().getToken()!!)
        viewModel.setId(getID())


        val toolbar = requireActivity().findViewById<Toolbar>(R.id.dashboard_toolbar)

        toolbar.setNavigationOnClickListener { _ ->
            OthersDialog(requireContext(), viewModel)
        }


        toolbar.setOnMenuItemClickListener(this)
        prepareList()

        // On NewAd Fab clicked
        createGroup()

         binding.refresher.setOnRefreshListener(this)

         binding.refresher.post {
             binding.refresher.isRefreshing = true
             prepareList()
         }


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
    }

    // Preparing both lists Others and Groups
    private fun prepareList() {
        val groupsAdapter = GroupsAdapter(requireContext(), viewModel.getId()!!)
        groupsAdapter.setStartChat(object : StartChat{
            override fun onStartChat(group: GroupResponse) {
                val intent = Intent(requireActivity(), GroupActivity::class.java)
                val mGroup =Gson().toJson(group)
                intent.putExtra(getString(R.string.group_id), mGroup)
                startActivity(intent)
            }
        })
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllGroups().let {
                Log.d(TAG, "prepareList: $it")
                val groupsList = viewModel.clearGroupsList(it)
                if (groupsList.isNotEmpty()) {
                    binding.noGroupsLayout.visibility = View.GONE
                }else binding.noGroupsLayout.visibility = View.VISIBLE

                // Todo Change back to GroupsList
                groupsAdapter.submitList(groupsList)
                setupGroupsRv(groupsAdapter)
                 binding.refresher.isRefreshing = false
            }
        }
    }



    private fun setupGroupsRv(iAdapter: GroupsAdapter) {
        binding.myGroups.apply {
            adapter = iAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun goToExplore() {

        Log.d(TAG, "goToExplore: clicked")
        val tabHost = requireActivity().findViewById<TabLayout>(R.id.tabs_layout)
        tabHost.getTabAt(1)!!.select()

    }

    private fun createGroup() {
        val dialog = AdDialog(requireContext())

        binding.createAdFb.setOnClickListener {
            dialog.createGroup { title, desc ->
                viewModel.setAdTitle(title)
                viewModel.setAdDescription(desc)
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        viewModel.createAd()
                    } catch (e: Exception) {
                        Log.d(TAG, "createGroup: ${e.message}")
                    } finally {
                        prepareList()
                    }

                }
            }
        }
    }



    override fun onRefresh() {
        prepareList()
    }



    companion object {
        private const val TAG = "GroupsFragment"
    }

    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when (p0?.itemId) {
            R.id.logout -> {
                (requireActivity() as DashboardActivity).logOut()
            }
            R.id.search -> {
                val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
                (p0.actionView as android.widget.SearchView).apply {
                    setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
                }

            }
        }
        return true
    }


}