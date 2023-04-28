package com.example.groupizer.ui.auth.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groupizer.domain.repository.AuthRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory(private val repository: AuthRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}