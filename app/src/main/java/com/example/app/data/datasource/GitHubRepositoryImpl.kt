package com.example.app.data.datasource

import com.example.app.data.api.IGitHubApiService
import com.example.app.data.mapper.GitHubUserMapper
import com.example.app.domain.model.UserModel
import com.example.app.domain.repository.IGitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GitHubRepositoryImpl(
    private val gitHubApiService: IGitHubApiService
) : IGitHubRepository {

    override suspend fun findByNickname(nickname: String): Result<UserModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = gitHubApiService.getUser(nickname)
                if (response.isSuccessful && response.body() != null) {
                    val userDto = response.body()!!
                    val userModel = GitHubUserMapper.fromDto(userDto)
                    Result.success(userModel)
                } else {
                    Result.failure(Exception("Error al obtener datos del usuario: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
