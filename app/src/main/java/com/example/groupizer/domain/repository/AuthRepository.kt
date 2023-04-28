package com.example.groupizer.domain.repository

import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.auth.LoginForm
import com.example.groupizer.pojo.model.auth.RegisterForm
import com.example.groupizer.pojo.model.interest.Interest
import com.example.groupizer.pojo.model.interest.InterestsResponse

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/28/2023.
 */
interface AuthRepository {
    suspend fun registerUser(form: RegisterForm): AuthResponse
    suspend fun loginUser(form: LoginForm): AuthResponse
    suspend fun getAllInterests(): List<InterestsResponse>
    suspend fun sendSelectedInterests(token: String, ids: List<Interest>): List<InterestsResponse>
}