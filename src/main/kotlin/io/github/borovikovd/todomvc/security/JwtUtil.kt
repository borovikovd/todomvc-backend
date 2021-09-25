package io.github.borovikovd.todomvc.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {
    companion object {
        const val ROLES_CLAIM = "roles"
    }

    @Value("\${jwt.secret}")
    lateinit var secret: String

    @Value("\${jwt.sessionTimeSec}")
    var sessionTime: Long = 0

    fun getAllClaimsFromToken(token: String): Claims? {
        return try {
            Jwts
                .parser()
                .setSigningKey(secret.toByteArray())
                .parseClaimsJws(token)?.body
        } catch (e: JwtException) {
            null
        }
    }

    fun generateToken(user: UserDetails): String {
        val now = Date()
        val expirationTime = Date(now.time + sessionTime * 1000)
        return Jwts
            .builder()
            .setSubject(user.username)
            .claim(ROLES_CLAIM, user.authorities.map { a -> a.authority })
            .setIssuedAt(now)
            .setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.HS256, secret.toByteArray())
            .compact()
    }
}
