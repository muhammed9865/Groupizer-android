package com.example.groupizer.ui.auth.register.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.example.groupizer.R
import com.example.groupizer.databinding.InterestCircleItemBinding
import com.example.groupizer.pojo.model.Interest
import com.example.groupizer.pojo.response.InterestsResponse

class InterestsViewHolder(
    private val context: Context,
    private val binding: InterestCircleItemBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: InterestsResponse, interestCheck: InterestCheck?) = with(item) {
        binding.apply {
            interestCheckbox.text = title

            if (interestCheckbox.text.length >= 8) {
                interestCheckbox.textSize = 14f
            }

            interestCheckbox.setOnCheckedChangeListener { _, b ->
                if (b) {
                    Log.d("Circle Item", "bind: ${interestCheckbox.isChecked}")
                    interestCheckbox.setTextColor(context.getColor(R.color.white))
                    interestCheck?.onInterestChecked(id, true)
                } else {
                    interestCheckbox.setTextColor(context.getColor(R.color.pink))
                    interestCheck?.onInterestChecked(id, false)
                }
            }

        }
    }


}