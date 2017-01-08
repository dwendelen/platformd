package com.github.dwendelen.platformd.infrastructure.authentication;

import com.github.dwendelen.platformd.rest.domain.user.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenService {
    private static String SECRET = "24n$#%oeoe843%5eo,ntour4/";

    public String create(User user) {
        Instant iss = Instant.now();
        Instant exp = iss.plus(1, ChronoUnit.DAYS);

        return Jwts.builder()
                .setSubject(user.getUserId().toString())
                .setId(UUID.randomUUID().toString())
                .setIssuer("platformd")
                .setIssuedAt(Date.from(iss))
                .setExpiration(Date.from(exp))
                .claim("admin", user.isAdmin())
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }


    public User parse(String token) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .requireIssuer("platformd")
                .parseClaimsJws(token);

        validateSigningAlgorithm(jws);
        Claims claims = jws.getBody();

        return new User()
                .setUserId(UUID.fromString(claims.getSubject()))
                .setAdmin(claims.get("admin", Boolean.class));

    }

    private void validateSigningAlgorithm(Jws<Claims> jws) {
        if(!jws.getHeader().getAlgorithm().equals(SignatureAlgorithm.HS256.getValue())) {
            throw new IllegalArgumentException("Wrong signing algorithm");
        }
    }
}
