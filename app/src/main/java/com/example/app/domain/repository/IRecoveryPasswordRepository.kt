package com.example.app.domain.repository

import com.example.app.domain.model.UserModel

interface IRecoveryPasswordRepository {
    suspend fun recoveryPasswordForAccount(email: String): Result<UserModel>
}