package com.example.app.domain.model

import com.example.app.domain.model.vo.UrlPath
import com.example.app.domain.model.vo.Nickname
import com.example.app.domain.model.vo.Name
import com.example.app.domain.model.vo.Bio
import com.example.app.domain.model.vo.Company
import com.example.app.domain.model.vo.Location

data class UserModel(
    val nickname: Nickname,
    val pathUrl: String,
    val name: Name? = null,
    val bio: Bio? = null,
    val company: Company? = null,
    val location: Location? = null,
    val publicRepos: Int = 0,
    val followers: Int = 0,
    val following: Int = 0,
    val htmlUrl: UrlPath? = null
)
