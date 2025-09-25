package com.example.app.domain.model.vo

@JvmInline
value class Nickname(val value: String) {
    init {
        require(value.length >= 3) { "El nickname debe tener al menos 3 caracteres" }
        require(!value.contains(" ")) { "El nickname no puede contener espacios" }
    }
    override fun toString(): String = value
}

@JvmInline
value class Name(val value: String) {
    init {
        require(value.isNotBlank()) { "El nombre no puede estar vacío" }
    }
    override fun toString(): String = value
}

@JvmInline
value class Bio(val value: String) {
    init {
        require(value.length <= 160) { "La bio no puede tener más de 160 caracteres" }
    }
    override fun toString(): String = value
}

@JvmInline
value class Company(val value: String) {
    init {
        require(value.isNotBlank()) { "La compañía no puede estar vacía" }
    }
    override fun toString(): String = value
}

@JvmInline
value class Location(val value: String) {
    init {
        require(value.isNotBlank()) { "La ubicación no puede estar vacía" }
    }
    override fun toString(): String = value
}

