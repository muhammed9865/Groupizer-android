package com.example.groupizer.pojo.di

import com.example.groupizer.domain.repository.AuthRepository
import com.example.groupizer.pojo.repository.AuthRepositoryImpl

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/28/2023.
 */
object DataModule {
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl.getInstance()
    }
}