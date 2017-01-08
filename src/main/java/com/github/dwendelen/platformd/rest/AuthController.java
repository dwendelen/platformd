package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.user.User;
import com.github.dwendelen.platformd.core.user.UserDao;
import com.github.dwendelen.platformd.infrastructure.authentication.GoogleTokenService;
import com.github.dwendelen.platformd.infrastructure.authentication.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private GoogleTokenService googleTokenService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces="application/json")
    public LoginResponse login(@RequestBody String googleToken) throws Exception {
        String googleId = googleTokenService.getGoogleId(googleToken);
        User user = userDao.get(googleId);

        String token = tokenService.create(user);
        return new LoginResponse()
                .setUserId(user.getUserId())
                .setAdmin(user.isAdmin())
                .setToken(token);
    }

    private static class LoginResponse {
        private UUID userId;
        private boolean isAdmin;
        private String token;

        public UUID getUserId() {
            return userId;
        }

        public LoginResponse setUserId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public LoginResponse setAdmin(boolean admin) {
            isAdmin = admin;
            return this;
        }

        public String getToken() {
            return token;
        }

        public LoginResponse setToken(String token) {
            this.token = token;
            return this;
        }
    }
}