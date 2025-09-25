package com.example.app.data.api

import com.example.app.data.api.dto.GitHubUserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IGitHubApiService {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String): Response<GitHubUserDto>
}