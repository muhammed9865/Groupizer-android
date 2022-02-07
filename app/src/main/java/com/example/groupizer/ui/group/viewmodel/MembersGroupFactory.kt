package com.example.groupizer.ui.group.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groupizer.pojo.repository.DashboardRepository

@Suppress("UNCHECKED_CAST")
class MembersGroupFactory(private val repository: DashboardRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GroupViewModel(repository) as T
    }
}