package io.github.borovikovd.todomvc.security

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthenticationManagerTest {
    val jwtUtil: JwtUtil = JwtUtil()

    init {
        jwtUtil.secret = "jwtSecret123456"
        jwtUtil.sessionTime = 1000
    }

    @Test
    fun `Parse Authorization`() {
        val token = testToken()

        val authenticationManager = AuthenticationManager(jwtUtil)
        val authentication = UsernamePasswordAuthenticationToken(token, token)
        val authResult = authenticationManager.authenticate(authentication).block()

        Assertions.assertNotNull(authResult)
        Assertions.assertEquals("test", authResult?.principal)
        Assertions.assertEquals("user", authResult?.authorities?.first()?.authority)
    }

    private fun testToken(): String {
        val ud = object : UserDetails {
            override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                return mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("user"))
            }

            override fun getPassword(): String {
                TODO("Not yet implemented")
            }

            override fun getUsername(): String {
                return "test"
            }

            override fun isAccountNonExpired(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isAccountNonLocked(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isCredentialsNonExpired(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isEnabled(): Boolean {
                TODO("Not yet implemented")
            }
        }

        return jwtUtil.generateToken(ud)
    }
}
