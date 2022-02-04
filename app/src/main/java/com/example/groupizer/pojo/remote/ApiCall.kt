package com.example.groupizer.pojo.remote

import android.util.JsonToken
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groupizer.Constants
import com.example.groupizer.pojo.model.Interest
import com.example.groupizer.pojo.model.LoginForm
import com.example.groupizer.pojo.model.RegisterForm
import com.example.groupizer.pojo.response.AuthResponse
import com.example.groupizer.pojo.response.InterestsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.await
import retrofit2.awaitResponse

class ApiCall {
    // Returns the interface as a callable functions
    private fun getService(): BackEndPoint {
        return ApiBuilder.buildService(BackEndPoint::class.java)
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
        val request = getService().sendSelectedInterests(token, ids)
        return request.await()
    }


    companion object {
        private const val TAG = "ApiCall"
    }
}