package com.example.groupizer.pojo.remote

import com.example.groupizer.pojo.model.Interest
import com.example.groupizer.pojo.model.LoginForm
import com.example.groupizer.pojo.model.RegisterForm
import com.example.groupizer.pojo.response.AuthResponse
import com.example.groupizer.pojo.response.InterestsResponse
import retrofit2.Call
import retrofit2.http.*

interface BackEndPoint {

    @POST("users/")
    fun registerUser(@Body registerForm: RegisterForm): Call<AuthResponse>

    @POST("users/login/")
    fun loginUser(@Body loginForm: LoginForm): Call<AuthResponse>

    @GET("interests/")
    fun getAllInterests(): Call<List<InterestsResponse>>

    @POST("interests/add/")
    fun sendSelectedInterests(
        @Header("Authorization") token: String,
        @Body id: List<Interest>
    ):Call<List<InterestsResponse>>


}