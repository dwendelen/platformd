package com.github.dwendelen.platformd.infrastructure.authentication

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.{Date, UUID}

import com.github.dwendelen.platformd.rest.domain.user.User
import io.jsonwebtoken.{Claims, Jws, Jwts, SignatureAlgorithm}
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TokenService(@Value("platformd.token.secret")
                   secret: String) {

    def create(user: User): String = {
        val iss = Instant.now
        val exp = iss.plus(1, ChronoUnit.DAYS)
        Jwts.builder
                .setSubject(user.user_Id.toString)
                .setId(UUID.randomUUID.toString)
                .setIssuer("platformd")
                .setIssuedAt(Date.from(iss))
                .setExpiration(Date.from(exp))
                .claim("admin", user.admin)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes)
                .compact
    }

    def parse(token: String): User = {
        val jws = Jwts.parser
                .setSigningKey(secret.getBytes)
                .requireIssuer("platformd")
                .parseClaimsJws(token)

        validateSigningAlgorithm(jws)
        val claims = jws.getBody

        new User(
            null,
            claims.get("admin", classOf[Boolean]),
            UUID.fromString(claims.getSubject)
        )
    }

    private def validateSigningAlgorithm(jws: Jws[Claims]) {
        if (jws.getHeader.getAlgorithm != SignatureAlgorithm.HS256.getValue) {
            throw new IllegalArgumentException("Wrong signing algorithm")
        }
    }
}