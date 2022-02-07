package com.example.groupizer.ui.group.members

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.groupizer.Constants
import java.lang.IndexOutOfBoundsException

class MembersFragmentsAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return Constants.MEMBERS_FRAG_COUNT
    }

    override fun createFragment(position: Int): Fragment {
       return when (position) {
            0 -> Members()
            1 -> Requests()
            else -> throw IndexOutOfBoundsException()
        }

    }
}