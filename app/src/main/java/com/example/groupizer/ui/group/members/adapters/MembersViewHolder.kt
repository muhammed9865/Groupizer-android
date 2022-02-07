package com.example.groupizer.ui.group.members.adapters

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.databinding.GroupMemberItemBinding
import com.example.groupizer.pojo.model.Roles
import com.example.groupizer.pojo.model.group.Membership

class MembersViewHolder(private val context: Context, private val binding: GroupMemberItemBinding) :
    RecyclerView.ViewHolder(binding.root), AdapterView.OnItemSelectedListener {
    private var newRole: String? = null
    fun bind(item: Membership, newRank: NewRank?) = with(binding) {
        groupMemberItemName.text = item.user?.name
        groupMemberItemEmail.text = item.user?.email
        newRole = item.role
        setUpRankSpinner()
        if (item.role == Roles.ADMIN) {
            rankSpiner.setSelection(0)
        }else rankSpiner.setSelection(1)

        binding.saveRank.setOnClickListener {
            if (item.role != newRole)
                newRank?.onRankChanged(item)
        }

    }

    private fun setUpRankSpinner() {
        ArrayAdapter.createFromResource(
            context,
            R.array.roles,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.rankSpiner.adapter = adapter
            binding.rankSpiner.onItemSelectedListener = this
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0 == binding.rankSpiner) {
            when (p0.selectedItemPosition) {
                0 -> {
                    newRole = Roles.ADMIN
                }
                1 -> {
                    newRole = Roles.MEMBER
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}