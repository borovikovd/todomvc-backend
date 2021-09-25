package io.github.borovikovd.todomvc.rest

import io.github.borovikovd.todomvc.repository.UserRepository
import io.github.borovikovd.todomvc.security.JwtUtil
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.security.Principal
import javax.validation.Valid
import javax.validation.constraints.NotBlank

data class LoginRequest(@NotBlank val email: String, @NotBlank val password: String)
data class LoginResponse(val token: String)

@RestController
class AuthResource(
    private val jwtUtil: JwtUtil,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): Mono<LoginResponse> {
        return userRepository.findByEmail(loginRequest.email).flatMap { user ->
            if (passwordEncoder.matches(loginRequest.password, user.passwordHash)) {
                val userDetails = User
                    .withUsername(loginRequest.email)
                    .password(loginRequest.password)
                    .roles("USER")
                    .build()
                val token = jwtUtil.generateToken(userDetails)
                Mono.just(LoginResponse(token))
            } else {
                Mono.empty()
            }
        }.switchIfEmpty { throw AccessDeniedException("Invalid username or password") }
    }

    @GetMapping("/me")
    fun me(principal: Principal): Mono<Principal> = Mono.just(principal)
}
