package io.github.borovikovd.todomvc.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.lang.UnsupportedOperationException

@Component
class SecurityContextRepository(private val authenticationManager: AuthenticationManager) :
    ServerSecurityContextRepository {

    companion object {
        const val AUTH_HEADER_PREFIX = "Bearer "
    }

    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw UnsupportedOperationException("save")
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        exchange?.request?.headers?.getFirst(HttpHeaders.AUTHORIZATION)?.let { header ->
            if (header.startsWith(AUTH_HEADER_PREFIX)) {
                val token = header.substring(AUTH_HEADER_PREFIX.length)
                val authentication = UsernamePasswordAuthenticationToken(token, token)
                return authenticationManager.authenticate(authentication).map { SecurityContextImpl(it) }
            }
        }
        return Mono.empty()
    }
}
