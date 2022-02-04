package com.example.groupizer.ui.auth.register.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groupizer.pojo.repository.GroupizerRepository

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory(private val repository: GroupizerRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }
}