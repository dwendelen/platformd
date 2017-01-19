package com.github.dwendelen.platformd.infrastructure.authentication

import java.io.IOException
import java.util.regex.Pattern
import javax.servlet._
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

object UserVerifyingFilter {
    private val logger = LoggerFactory.getLogger(classOf[UserVerifyingFilter])
    private val authPattern = Pattern.compile("^/api/auth/.*$")
}

@Component
class UserVerifyingFilter(identityProvider: IdentityProvider, tokenService: TokenService) extends Filter {

    def init(filterConfig: FilterConfig) {}

    def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (!request.isInstanceOf[HttpServletRequest]) {
            throw new ServletException(request + " not HttpServletRequest")
        }
        if (!response.isInstanceOf[HttpServletResponse]) {
            throw new ServletException(request + " not HttpServletResponse")
        }
        doFilter(request.asInstanceOf[HttpServletRequest], response.asInstanceOf[HttpServletResponse], chain)
    }

    def doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        if (mustBeAuthenticated(request)) {
            val valid = authenticate(request, response)
            if (!valid) {
                return
            }
        }
        try {
            chain.doFilter(request, response)
        } finally {
            identityProvider.clearCurrentUser()
        }
    }

    private def mustBeAuthenticated(request: HttpServletRequest): Boolean = {
        if (UserVerifyingFilter.authPattern.matcher(request.getServletPath).matches) {
            return false
        }

        true
    }

    private def authenticate(request: HttpServletRequest, response: HttpServletResponse): Boolean = {
        val token = request.getHeader("Token")
        if (token == null) {
            failed(response, "No token found")
            return false
        }
        try {
            val user = tokenService.parse(token)
            identityProvider.setCurrentUser(user)
            true
        } catch {
            case e: Exception =>
                UserVerifyingFilter.logger.warn("Bad token: " + token, e)
                failed(response, "Could not authenticate user", e)
                false
        }
    }

    private def failed(response: HttpServletResponse, reason: String) {
        UserVerifyingFilter.logger.warn(reason)
        response.sendError(401, reason)
    }

    private def failed(response: HttpServletResponse, reason: String, e: Throwable) {
        UserVerifyingFilter.logger.warn(reason, e)
        response.sendError(401, reason)
    }

    def destroy() {
    }
}
