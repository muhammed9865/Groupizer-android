package com.example.groupizer.pojo.repository

import com.example.groupizer.pojo.model.interest.Interest
import com.example.groupizer.pojo.model.auth.LoginForm
import com.example.groupizer.pojo.model.auth.RegisterForm
import com.example.groupizer.pojo.remote.auth.AuthApiCall


class AuthRepository(private val authApi: AuthApiCall) {

    suspend fun registerUser(registerForm: RegisterForm) = authApi.registerUser(registerForm)

    suspend fun loginUser(loginForm: LoginForm) = authApi.loginUser(loginForm)

    suspend fun getAllInterests() = authApi.getAllInterests()

    suspend fun sendSelectedInterests(auth_token: String, ids: List<Interest>) = authApi.sendSelectedInterests(auth_token, ids)

    companion object {
        private val lock = Any()
        private var instance: AuthRepository? = null
        fun getInstance(): AuthRepository {
            return instance ?: synchronized(lock) {
                instance ?: AuthRepository(AuthApiCall()).also { instance = it }
            }
        }
    }
}