package com.example.groupizer.pojo.remote.auth

import com.example.groupizer.pojo.model.interest.InterestsList
import com.example.groupizer.pojo.model.auth.LoginForm
import com.example.groupizer.pojo.model.auth.RegisterForm
import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.interest.InterestsResponse
import retrofit2.Call
import retrofit2.http.*

interface AuthEndPoint {

    @POST("users/")
    fun registerUser(@Body registerForm: RegisterForm): Call<AuthResponse>

    @POST("users/login/")
    fun loginUser(@Body loginForm: LoginForm): Call<AuthResponse>

    @GET("interests/")
    fun getAllInterests(): Call<List<InterestsResponse>>

    @POST("interests/subscribe/")
    fun sendSelectedInterests(
        @Header("Authorization") token: String,
        @Body id: InterestsList
    ):Call<List<InterestsResponse>>





}