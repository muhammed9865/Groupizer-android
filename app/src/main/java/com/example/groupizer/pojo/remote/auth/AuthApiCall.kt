package com.example.groupizer.pojo.remote.auth

import android.util.Log
import com.example.groupizer.Constants
import com.example.groupizer.pojo.model.interest.Interest
import com.example.groupizer.pojo.model.interest.InterestsList
import com.example.groupizer.pojo.model.auth.LoginForm
import com.example.groupizer.pojo.model.auth.RegisterForm
import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.interest.InterestsResponse
import com.example.groupizer.pojo.remote.ApiBuilder
import retrofit2.await

class AuthApiCall {
    // Returns the interface as a callable functions
    private fun getService(): AuthEndPoint {
        return ApiBuilder.buildService(AuthEndPoint::class.java)
    }


    suspend fun registerUser(registerForm: RegisterForm): AuthResponse {
        val request = getService().registerUser(registerForm)
        return request.await()
    }


    suspend fun loginUser(loginForm: LoginForm): AuthResponse {
        val request = getService().loginUser(loginForm)
        return request.await()
    }

    suspend fun getAllInterests(): List<InterestsResponse> {
        val request = getService().getAllInterests()
        return request.await()

    }


    suspend fun sendSelectedInterests(
        auth_token: String,
        ids: List<Interest>
    ): List<InterestsResponse> {
        val token = "${Constants.BEARER} $auth_token"
        Log.d(TAG, "sendSelectedInterests: $token")
        // Get the id of each interest <easy peasy>
        // instead of changing the classes
        val list =  ids.map { it.id }
        val listOfIds = InterestsList(list)

        val request = getService().sendSelectedInterests(token, listOfIds)
        return request.await()
    }


    companion object {
        private const val TAG = "ApiCall"
    }
}