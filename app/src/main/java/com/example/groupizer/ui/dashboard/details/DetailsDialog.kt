package com.example.groupizer.ui.dashboard.details

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.example.groupizer.databinding.FragmentDetailsBinding
import com.example.groupizer.pojo.model.ad.AdResponse

class DetailsDialog(private val ad: AdResponse,context: Context, theme: Int) : Dialog(context, theme) {
    private val binding = FragmentDetailsBinding.inflate(LayoutInflater.from(context))
    val join = binding.detailsJoinBtn
    init {
        show()
        setContentView(binding.root)
        prepareDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        closeDialog()

    }




    private fun closeDialog() {
        binding.detailsBackBtn.setOnClickListener {
            dismiss()
            cancel()
        }
    }

    private fun prepareDialog() {
        binding.detailsGroupName.text = ad.title
        binding.detailsOwnerEmail.text = ad.user.email
        binding.groupDescription.text = ad.description
        binding.groupOwnerName.text = ad.user.name

        binding.groupMembersCount.text = ad.group.membership.size.toString()

    }


}