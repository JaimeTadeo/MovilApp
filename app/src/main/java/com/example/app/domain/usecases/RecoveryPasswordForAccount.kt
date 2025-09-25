package com.example.app.domain.usecases

import com.example.app.domain.model.UserModel
import com.example.app.domain.repository.IRecoveryPasswordRepository

class RecoveryPasswordForAccount(
    private val repository: IRecoveryPasswordRepository
) {
    suspend operator fun invoke(email: String): Result<UserModel> {
        return repository.recoveryPasswordForAccount(email)
    }
}