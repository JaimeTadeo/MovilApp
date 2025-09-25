package com.example.app.domain.model

import com.example.app.domain.model.vo.*
import org.junit.Assert.*
import org.junit.Test

class UserModelTest {
    @Test
    fun `puede crear un UserModel válido`() {
        val user = UserModel(
            nickname = Nickname("usuarioValido"),
            pathUrl = "https://github.com/usuarioValido",
            name = Name("Juan Pérez"),
            bio = Bio("Desarrollador"),
            company = Company("OpenAI"),
            location = Location("Bolivia"),
            publicRepos = 10,
            followers = 100,
            following = 50,
            htmlUrl = UrlPath("https://github.com/usuarioValido")
        )
        assertEquals("usuarioValido", user.nickname.value)
        assertEquals("Juan Pérez", user.name?.value)
        assertEquals("Desarrollador", user.bio?.value)
        assertEquals("OpenAI", user.company?.value)
        assertEquals("Bolivia", user.location?.value)
        assertEquals(10, user.publicRepos)
        assertEquals(100, user.followers)
        assertEquals(50, user.following)
        assertEquals("https://github.com/usuarioValido", user.htmlUrl?.value)
    }
}

