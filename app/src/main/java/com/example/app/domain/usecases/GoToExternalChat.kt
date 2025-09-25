package com.example.app.domain.usecases

import com.example.app.domain.model.UserModel
import com.example.app.domain.repository.IExternalChatRepository

class GoToExternalChat(
    private val repository: IExternalChatRepository
) {
    suspend operator fun invoke(justTest: String): Result<UserModel> {
        return repository.goToExternalChat(justTest)
    }
}