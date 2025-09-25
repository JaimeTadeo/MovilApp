package com.example.app.domain.repository

import com.example.app.domain.model.UserModel

interface IGitHubRepository {
    suspend fun findByNickname(nickname: String): Result<UserModel>
}
