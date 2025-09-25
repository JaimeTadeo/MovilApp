package com.example.app.domain.model

import com.example.app.domain.model.vo.Nickname
import org.junit.Assert.assertThrows
import org.junit.Test

class GithubNickTest {
    @Test
    fun nicknameValidoNoLanzaExcepcion() {
        UserModel(nickname = Nickname("usuarioValido"), pathUrl = "url")
    }

    @Test
    fun nicknameConEspaciosLanzaExcepcion() {
        assertThrows(IllegalArgumentException::class.java) {
            UserModel(nickname = Nickname("usuario invalido"), pathUrl = "url")
        }
    }

    @Test
    fun nicknameMenosDeTresCaracteresLanzaExcepcion() {
        assertThrows(IllegalArgumentException::class.java) {
            UserModel(nickname = Nickname("ab"), pathUrl = "url")
        }
    }
}
