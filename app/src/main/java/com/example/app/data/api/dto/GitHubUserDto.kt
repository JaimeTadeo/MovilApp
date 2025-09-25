package com.example.app.data.api.dto

import com.google.gson.annotations.SerializedName

data class GitHubUserDto(
    @SerializedName("login")
    val nickname: String,

    @SerializedName("avatar_url")
    val pathUrl: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("bio")
    val bio: String?,

    @SerializedName("company")
    val company: String?,

    @SerializedName("location")
    val location: String?,

    @SerializedName("public_repos")
    val publicRepos: Int,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("following")
    val following: Int,

    @SerializedName("html_url")
    val htmlUrl: String?
)
