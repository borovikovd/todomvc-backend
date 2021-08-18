package io.github.borovikovd.todomvc.model

import org.springframework.data.annotation.Id

data class User(
    @Id val id: Long = 0,
    val email: String,
    val passwordHash: String
)
