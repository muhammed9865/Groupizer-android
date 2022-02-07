package com.example.groupizer.ui.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groupizer.pojo.repository.DashboardRepository

@Suppress("UNCHECKED_CAST")
class GroupsViewModelFactory(private val repository: DashboardRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SharedGroupsViewModel(repository) as T
    }
}