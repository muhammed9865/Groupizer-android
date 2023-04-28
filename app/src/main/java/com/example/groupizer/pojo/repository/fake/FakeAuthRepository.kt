package com.example.groupizer.pojo.repository.fake

import com.example.groupizer.domain.repository.AuthRepository
import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.auth.LoginForm
import com.example.groupizer.pojo.model.auth.RegisterForm
import com.example.groupizer.pojo.model.interest.Interest
import com.example.groupizer.pojo.model.interest.InterestsResponse

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/28/2023.
 */
class FakeAuthRepository : AuthRepository {
    override suspend fun registerUser(form: RegisterForm): AuthResponse {
        return AuthResponse(
            1,
            "fake@email.com",
            "John Smith",
            "fakePassword21341",
            "fakeToken1234"
        )
    }

    override suspend fun loginUser(form: LoginForm): AuthResponse {
        return AuthResponse(
            1,
            "fake@email.com",
            "John Smith",
            "fakePassword21341",
            "fakeToken1234"
        )
    }

    override suspend fun getAllInterests(): List<InterestsResponse> {
        return listOf(
            InterestsResponse(1, "fakeInterest1"),
            InterestsResponse(2, "fakeInterest2"),
        )
    }

    override suspend fun sendSelectedInterests(
        token: String,
        ids: List<Interest>,
    ): List<InterestsResponse> {
        return listOf(
            InterestsResponse(1, "fakeInterest1"),
            InterestsResponse(2, "fakeInterest2"),
        )
    }
}