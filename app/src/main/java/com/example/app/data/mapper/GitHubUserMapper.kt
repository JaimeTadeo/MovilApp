package com.example.app.data.mapper

import com.example.app.data.api.dto.GitHubUserDto
import com.example.app.domain.model.UserModel
import com.example.app.domain.model.vo.Nickname
import com.example.app.domain.model.vo.Name
import com.example.app.domain.model.vo.Bio
import com.example.app.domain.model.vo.Company
import com.example.app.domain.model.vo.Location
import com.example.app.domain.model.vo.UrlPath

object GitHubUserMapper {
    fun fromDto(dto: GitHubUserDto): UserModel {
        return UserModel(
            nickname = Nickname(dto.nickname),
            pathUrl = dto.pathUrl,
            name = dto.name?.let { Name(it) },
            bio = dto.bio?.let { Bio(it) },
            company = dto.company?.let { Company(it) },
            location = dto.location?.let { Location(it) },
            publicRepos = dto.publicRepos,
            followers = dto.followers,
            following = dto.following,
            htmlUrl = dto.htmlUrl?.let { UrlPath(it) }
        )
    }
}
