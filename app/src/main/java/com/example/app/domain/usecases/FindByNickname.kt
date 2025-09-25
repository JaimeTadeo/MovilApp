package com.example.app.domain.usecases

import com.example.app.domain.model.UserModel
import com.example.app.domain.repository.IGitHubRepository

class FindByNickname(
    private val repository: IGitHubRepository
) {
    suspend operator fun invoke(nickname: String): Result<UserModel> {
        return repository.findByNickname(nickname)
    }
}