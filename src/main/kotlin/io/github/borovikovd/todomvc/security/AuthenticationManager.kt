package io.github.borovikovd.todomvc.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.Collections
import java.util.Date

@Component
class AuthenticationManager(private val jwtUtil: JwtUtil) : ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        authentication?.let {
            val token = it.credentials.toString()
            val claims = jwtUtil.getAllClaimsFromToken(token)
            if (claims != null && claims.expiration.after(Date())) {
                @Suppress("UNCHECKED_CAST")
                val roles = claims.get(JwtUtil.ROLES_CLAIM, List::class.java) as List<String>?
                    ?: Collections.emptyList()
                val authToken = UsernamePasswordAuthenticationToken(
                    claims.subject,
                    null,
                    roles.map { r -> SimpleGrantedAuthority(r) }
                )
                return Mono.just(authToken)
            }
        }
        return Mono.empty()
    }
}
