package com.example.groupizer.ui.auth.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupizer.pojo.model.auth.LoginForm
import com.example.groupizer.pojo.repository.AuthRepository
import com.example.groupizer.pojo.model.auth.AuthResponse

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password


    fun setEmail(email: String) {

        this._email.value = email
        Log.d(TAG, "login setEmail: ${_email.value}")

    }

    fun setPassword(password: String) {
        this._password.value = password
        Log.d(TAG, "login setPassword: ${_password.value}")
    }


    suspend fun login(): AuthResponse {
        val loginForm = LoginForm(_email.value!!, _password.value!!)
        return repository.loginUser(loginForm)
    }


    companion object {
        private const val TAG = "LoginViewModel"
    }

}