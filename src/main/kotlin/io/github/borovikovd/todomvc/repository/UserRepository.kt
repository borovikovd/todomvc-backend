package io.github.borovikovd.todomvc.repository

import io.github.borovikovd.todomvc.model.User
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<User, Long> {
    fun findByEmail(email: String): Mono<User>
}
