package io.github.borovikovd.todomvc.model

import org.springframework.data.annotation.Id
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

data class User(
    @Id val id: Long = 0,
    val email: String,
    val passwordHash: String
) {
    fun toUserDetails(): UserDetails = User.withUsername(email).password(passwordHash).build()
}
