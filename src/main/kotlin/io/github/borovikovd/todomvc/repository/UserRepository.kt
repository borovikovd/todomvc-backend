package io.github.borovikovd.todomvc.repository

import io.github.borovikovd.todomvc.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface UserRepository : ReactiveCrudRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
}
