package com.github.dwendelen.platformd.infrastructure.authentication;

import com.github.dwendelen.platformd.core.user.User;
import com.github.dwendelen.platformd.core.user.UserDao;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
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
                .setIssuedAt(Date.from(iss))
                .setExpiration(Date.from(exp))
                .claim("name", "John Doe")
                .claim("admin", user.isAdmin())
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }


    public User parse(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return new User()
                .setUserId(UUID.fromString(claims.getSubject()))
                .setAdmin(claims.get("admin", Boolean.class));

    }
}
