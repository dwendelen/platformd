package com.github.dwendelen.platformd.rest

import com.github.dwendelen.platformd.infrastructure.authentication.GoogleTokenService
import com.github.dwendelen.platformd.infrastructure.authentication.TokenService
import com.github.dwendelen.platformd.rest.domain.user.UserDao
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(Array("/api/auth"))
class AuthController(userDao: UserDao,
                     tokenService: TokenService,
                     googleTokenService: GoogleTokenService) {
    val logger = LoggerFactory.getLogger(classOf[AuthController])

    @RequestMapping(value = Array("/login"), method = Array(RequestMethod.POST), produces = Array("application/json"))
    def login(@RequestBody googleToken: String): LoginResponse = {
        val googleId = googleTokenService.getGoogleId(googleToken)
        val user = userDao.get(googleId)
        if (user == null) {
            logger.info("Unknown google id: " + googleId)
            throw new RuntimeException("Unknown google id: " + googleId)
        }
        val token = tokenService.create(user)
        new LoginResponse(
            user.user_Id,
            user.admin,
            token)
    }

    class LoginResponse(var userId: UUID,
                        var isAdmin: Boolean,
                        var token: String) {
        def this() = this(null, false, null)
    }
}