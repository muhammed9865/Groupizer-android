package com.example.groupizer.ui.auth.register.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.groupizer.databinding.InterestCircleItemBinding
import com.example.groupizer.pojo.model.interest.InterestsResponse

class InterestsAdapter(private val context: Context):ListAdapter<InterestsResponse, InterestsViewHolder>(InterestsDiff()) {
    private var interestCheck: InterestCheck? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestsViewHolder {
        val binding = InterestCircleItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return InterestsViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: InterestsViewHolder, position: Int) {
        holder.bind(getItem(position), interestCheck)
    }

    fun setInterestCheck(interestCheck: InterestCheck) {
        this.interestCheck = interestCheck
    }
}