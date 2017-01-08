package com.github.dwendelen.platformd.infrastructure.authentication;

import com.github.dwendelen.platformd.core.user.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserVerifyingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserVerifyingFilter.class);

    @Autowired
    private IdentityProvider identityProvider;
    @Autowired
    private TokenService tokenService;

    private static Pattern authPattern = Pattern.compile("^/api/auth/.*$");
    private static Pattern userPattern = Pattern.compile("^/api/users/([a-zA-Z0-9\\-]*)/?.*$");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException(request + " not HttpServletRequest");
        }
        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException(request + " not HttpServletResponse");
        }

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(mustBeAuthenticated(request)) {
            boolean valid = authenticate(request, response);
            if(!valid) {
                return;
            }
        }
        try {
            chain.doFilter(request, response);
        } finally {
            identityProvider.clearCurrentUser();
        }
    }

    private boolean mustBeAuthenticated(HttpServletRequest request) {
        if(matches(authPattern, request)) {
            return false;
        }

        return true;
    }

    private boolean matches(Pattern pattern, HttpServletRequest request) {
        return pattern.matcher(request.getServletPath()).matches();
    }

    private boolean authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String token = request.getHeader("Token");
        if (token == null) {
            failed(response, "No token found");
            return false;
        }

        try {
            User user = tokenService.parse(token);
            identityProvider.setCurrentUser(user);

            return validateUserPath(request, user);
        } catch (Exception e) {
            logger.warn("Bad token: " + token, e);
            failed(response, "Could not authenticate user", e);
            return false;
        }
    }

    private boolean validateUserPath(HttpServletRequest request, User user) {
        if(user.isAdmin()) {
            return true;
        }
        Matcher matcher = userPattern.matcher(request.getServletPath());
        if(!matcher.matches()) {
            return true;
        }
        String userInUrl = matcher.group(1);
        return user.getUserId().toString().equals(userInUrl);
    }

    private void failed(HttpServletResponse response, String reason) throws IOException {
        logger.warn(reason);
        response.sendError(401, reason);
    }

    private void failed(HttpServletResponse response, String reason, Throwable e) throws IOException {
        logger.warn(reason, e);
        response.sendError(401, reason);
    }

    @Override
    public void destroy() {

    }
}
