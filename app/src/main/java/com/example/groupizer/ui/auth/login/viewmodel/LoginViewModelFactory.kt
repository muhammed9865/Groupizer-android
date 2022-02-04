package com.example.groupizer.ui.auth.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groupizer.pojo.repository.GroupizerRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val repository: GroupizerRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}