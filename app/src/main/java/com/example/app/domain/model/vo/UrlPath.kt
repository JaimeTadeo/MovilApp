package com.example.app.domain.model.vo

@JvmInline
value class UrlPath (val value: String) {
    init {
        require(value.startsWith("http://") || value.startsWith("https://")) {
            "La URL debe comenzar con http:// o https://"
        }
    }

    override fun toString(): String = value
}