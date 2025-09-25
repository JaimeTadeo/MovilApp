package com.example.app.domain.model.vo

import org.junit.Assert.*
import org.junit.Test

class ValueObjectsTest {
    @Test
    fun `name válido no lanza excepción`() {
        Name("Juan Pérez")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `name vacío lanza excepción`() {
        Name("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `name muy corto lanza excepción`() {
        Name("J")
    }

    @Test
    fun `bio válida no lanza excepción`() {
        Bio("Desarrollador de software")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `bio muy larga lanza excepción`() {
        Bio("a".repeat(161))
    }

    @Test
    fun `company válida no lanza excepción`() {
        Company("OpenAI")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `company muy larga lanza excepción`() {
        Company("a".repeat(51))
    }

    @Test
    fun `location válida no lanza excepción`() {
        Location("Bolivia")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `location muy larga lanza excepción`() {
        Location("a".repeat(51))
    }
}

