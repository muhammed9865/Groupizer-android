package com.example.groupizer.ui.dashboard.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.groupizer.Constants
import com.example.groupizer.ui.dashboard.explore.ExploreFragment
import com.example.groupizer.ui.dashboard.home.GroupsFragment
import java.lang.NullPointerException

class FragmentsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return Constants.DASHBOARD_FRAGS_COUNT
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> GroupsFragment()
            1 -> ExploreFragment()
            else -> throw NullPointerException()
        }
    }
}