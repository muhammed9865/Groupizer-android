package com.example.groupizer.ui.dashboard.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupizer.R
import com.example.groupizer.databinding.OthersDialogBinding
import com.example.groupizer.pojo.model.group.GroupResponse
import com.example.groupizer.ui.dashboard.home.others_adapter.OthersAdapter
import com.example.groupizer.ui.dashboard.viewmodel.SharedGroupsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OthersDialog(context: Context, viewModel: SharedGroupsViewModel): Dialog(context, R.style.PauseDialog) {
    private val binding = OthersDialogBinding.inflate(LayoutInflater.from(context))
    init {
        setContentView(binding.root)
        show()
        setupRecyclerView(viewModel)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.statusBarColor = context.getColor(R.color.bright_blue)
        binding.closeOthersDialog.setOnClickListener {
            dismiss()
            cancel()
        }
    }

    private fun setupRecyclerView(viewModel: SharedGroupsViewModel) {
        val iAdapter = OthersAdapter(context, viewModel.getId()!!)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getAllGroups().let {
                val list = viewModel.clearOthersList(it)
                iAdapter.submitList(list)
            }

            binding.othersRv.apply {
                adapter = iAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

}