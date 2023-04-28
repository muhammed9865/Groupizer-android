package com.example.groupizer.data.repository

import com.example.groupizer.pojo.model.auth.AuthResponse
import com.example.groupizer.pojo.model.auth.LoginForm
import com.example.groupizer.pojo.model.auth.RegisterForm
import com.example.groupizer.pojo.model.interest.Interest
import com.example.groupizer.pojo.model.interest.InterestsResponse
import com.example.groupizer.pojo.repository.fake.FakeAuthRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/28/2023.
 */
class FakeAuthRepositoryTest {
    private val repository = FakeAuthRepository()

    @Test
    fun testRegisterUser() = runBlocking {
        val form = RegisterForm("fake@email.com", "fakePassword21341")
        val expectedResponse = AuthResponse(
            1,
            "fake@email.com",
            "John Smith",
            "fakePassword21341",
            "fakeToken1234"
        )

        val actualResponse = repository.registerUser(form)

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun testLoginUser() = runBlocking {
        val form = LoginForm("fake@email.com", "fakePassword21341")
        val expectedResponse = AuthResponse(
            1,
            "fake@email.com",
            "John Smith",
            "fakePassword21341",
            "fakeToken1234"
        )

        val actualResponse = repository.loginUser(form)

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun testGetAllInterests() = runBlocking {
        val expectedResponse = listOf(
            InterestsResponse(1, "fakeInterest1"),
            InterestsResponse(2, "fakeInterest2"),
        )

        val actualResponse = repository.getAllInterests()

        assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun testSendSelectedInterests() = runBlocking {
        val token = "fakeToken1234"
        val ids = listOf(Interest(1), Interest(2))
        val expectedResponse = listOf(
            InterestsResponse(1, "fakeInterest1"),
            InterestsResponse(2, "fakeInterest2"),
        )

        val actualResponse = repository.sendSelectedInterests(token, ids)

        assertEquals(expectedResponse, actualResponse)
    }
}