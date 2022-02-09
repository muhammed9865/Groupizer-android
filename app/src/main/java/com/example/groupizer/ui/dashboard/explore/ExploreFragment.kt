package com.example.groupizer.ui.dashboard.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.groupizer.R
import com.example.groupizer.databinding.FragmentExploreBinding
import com.example.groupizer.pojo.model.ad.AdResponse
import com.example.groupizer.pojo.model.group.GroupApply
import com.example.groupizer.pojo.repository.DashboardRepository
import com.example.groupizer.ui.dashboard.explore.adapter.AdsAdapter
import com.example.groupizer.ui.dashboard.explore.adapter.ViewAd
import com.example.groupizer.ui.dashboard.viewmodel.GroupsViewModelFactory
import com.example.groupizer.ui.dashboard.viewmodel.SharedGroupsViewModel
import com.example.groupizer.ui.dashboard.details.DetailsDialog
import com.example.groupizer.ui.showError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ExploreFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: SharedGroupsViewModel by activityViewModels {
        GroupsViewModelFactory(DashboardRepository.getInstance())
    }
    private lateinit var binding: FragmentExploreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        binding.adsRefresher.setOnRefreshListener(this)

        binding.adsRefresher.post {
            binding.adsRefresher.isRefreshing = true
            prepareAdsAdapter()
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun prepareAdsAdapter() {
        val adapter = AdsAdapter()
        adapter.setViewAd(object : ViewAd {
            override fun onViewAdClicked(ad: AdResponse) {
                viewModel.setAd(ad)
                val dialog = DetailsDialog(ad, requireContext(), R.style.PauseDialog)
                    dialog.show()
                    dialog.join.setOnClickListener {
                        CoroutineScope(Dispatchers.Main).launch {
                            viewModel.apply(GroupApply(ad.group.id))
                            dialog.dismiss()
                            showError(binding.root, "${ad.group.id}")
                    }
                }

            }
        })
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllAds().let {
                val clearedList = viewModel.clearAdsList(it)
                adapter.submitList(clearedList)
                setupAdsRv(adapter)
                binding.adsRefresher.isRefreshing = false
            }


        }
    }

    private fun setupAdsRv(iAdapter: AdsAdapter) {
        binding.adsRv.apply {
            adapter = iAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onRefresh() {
        prepareAdsAdapter()
    }

   companion object {
       private const val TAG = "ExploreFragment"
   }

}