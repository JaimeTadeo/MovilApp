package com.example.app.domain.usecases

import com.example.app.domain.model.UserModel
import com.example.app.domain.model.vo.Nickname
import com.example.app.domain.repository.IGitHubRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class FindByNicknameTest {
    class FakeGitHubRepository : IGitHubRepository {
        override suspend fun findByNickname(nickname: String): Result<UserModel> {
            return if (nickname == "usuarioValido") {
                Result.success(
                    UserModel(
                        nickname = Nickname("usuarioValido"),
                        pathUrl = "https://github.com/usuarioValido"
                    )
                )
            } else {
                Result.failure(Exception("No encontrado"))
            }
        }
    }

    @Test
    fun `devuelve usuario cuando existe`() = runBlocking {
        val useCase = FindByNickname(FakeGitHubRepository())
        val result = useCase.invoke("usuarioValido")
        assertTrue(result.isSuccess)
        assertEquals("usuarioValido", result.getOrNull()?.nickname?.value)
    }

    @Test
    fun `devuelve error cuando no existe`() = runBlocking {
        val useCase = FindByNickname(FakeGitHubRepository())
        val result = useCase.invoke("otroUsuario")
        assertTrue(result.isFailure)
    }
}
