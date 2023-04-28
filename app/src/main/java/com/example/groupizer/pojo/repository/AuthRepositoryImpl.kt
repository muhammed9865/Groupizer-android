package com.example.groupizer.pojo.repository

import com.example.groupizer.domain.repository.AuthRepository
import com.example.groupizer.pojo.model.interest.Interest
import com.example.groupizer.pojo.model.auth.LoginForm
import com.example.groupizer.pojo.model.auth.RegisterForm
import com.example.groupizer.pojo.remote.auth.AuthApiCall


class AuthRepositoryImpl(private val authApi: AuthApiCall) : AuthRepository {

    override suspend fun registerUser(form: RegisterForm) = authApi.registerUser(form)

    override suspend fun loginUser(form: LoginForm) = authApi.loginUser(form)

    override suspend fun getAllInterests() = authApi.getAllInterests()

    override suspend fun sendSelectedInterests(token: String, ids: List<Interest>) = authApi.sendSelectedInterests(token, ids)

    companion object {
        private val lock = Any()
        private var instance: AuthRepository? = null
        fun getInstance(): AuthRepository {
            return instance ?: synchronized(lock) {
                instance ?: AuthRepositoryImpl(AuthApiCall()).also { instance = it }
            }
        }
    }
}