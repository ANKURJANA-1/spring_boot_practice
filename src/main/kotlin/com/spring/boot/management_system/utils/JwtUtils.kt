package com.spring.boot.management_system.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.function.Function

class JwtUtils {

    private val ACCESS_TOKEN_VALIDITY_SECONDS = (5 * 60 * 60).toLong()
    private val SIGNING_KEY = "devglan123r"
    private val TOKEN_PREFIX = "Bearer "
    private val HEADER_STRING = "Authorization"

    fun getUsernameFromToken(token: String?): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

   fun getExpirationDateFromToken(token: String?): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.expiration }
    }

    fun <T> getClaimFromToken(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String?): Claims {
        return Jwts.parser()
            .setSigningKey(SIGNING_KEY)
            .parseClaimsJws(token)
            .body
    }

    private fun isTokenExpired(token: String?): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    fun generateToken(user: User): String? {
        return user.username?.let { doGenerateToken(it) }
    }

    private fun doGenerateToken(subject: String): String {
        val claims = Jwts.claims().setSubject(subject)
        claims["scopes"] = listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
        return Jwts.builder()
            .setClaims(claims)
            .setIssuer("http://devglan.com")
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
            .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
            .compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }
}