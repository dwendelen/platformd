package com.github.dwendelen.platformd.infrastructure.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class GoogleFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(GoogleFilter.class);

    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Arrays.asList("714940765820-4ronadar6r8rjgkn0lih986sgi14vl0o.apps.googleusercontent.com"))
                .setIssuer("accounts.google.com")
                .build();
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

    public Void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Token");
        if(token == null) {
            return failed(response, "No token found");
        }

        try {
            GoogleIdToken verify = googleIdTokenVerifier.verify(token);
            String myId = verify.getPayload().getSubject();
            if(!"110479258309610389601".equals(myId)) {
                return failed(response, "Unknown user: " + myId);
            }
        } catch (Exception e) {
            logger.warn("Bad token: " + token, e);
            return failed(response, "Could not authenticate user", e);
        }

        return success(request, response, chain);
    }

    private Void success(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        return null;
    }

    private Void failed(HttpServletResponse response, String reason) throws IOException {
        logger.warn(reason);
        response.sendError(401, reason);
        return null;
    }

    private Void failed(HttpServletResponse response, String reason, Throwable e) throws IOException {
        logger.warn(reason, e);
        response.sendError(401, reason);
        return null;
    }

    @Override
    public void destroy() {

    }
}
