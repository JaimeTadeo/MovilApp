package com.example.app.domain.repository

import com.example.app.domain.model.UserModel

interface IExternalChatRepository {
    suspend fun goToExternalChat(openChat: String): Result<UserModel>
}