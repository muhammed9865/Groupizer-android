package com.example.groupizer.pojo.repository

import android.content.Context
import com.example.groupizer.pojo.model.Interest
import com.example.groupizer.pojo.model.LoginForm
import com.example.groupizer.pojo.model.RegisterForm
import com.example.groupizer.pojo.remote.ApiCall


class GroupizerRepository(private val api: ApiCall) {

    suspend fun registerUser(registerForm: RegisterForm) = api.registerUser(registerForm)

    suspend fun loginUser(loginForm: LoginForm) = api.loginUser(loginForm)

    suspend fun getAllInterests() = api.getAllInterests()

    suspend fun sendSelectedInterests(auth_token: String, ids: List<Interest>) = api.sendSelectedInterests(auth_token, ids)

    companion object {
        private val lock = Any()
        private var instance: GroupizerRepository? = null
        fun getInstance(): GroupizerRepository {
            return instance ?: synchronized(lock) {
                instance ?: GroupizerRepository(ApiCall()).also { instance = it }
            }
        }
    }
}