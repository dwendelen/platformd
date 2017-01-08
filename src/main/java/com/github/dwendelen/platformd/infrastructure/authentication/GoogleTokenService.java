package com.github.dwendelen.platformd.infrastructure.authentication;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

//Daans google id: 110479258309610389601
@Component
public class GoogleTokenService {
    private GoogleIdTokenVerifier googleIdTokenVerifier;

    @PostConstruct
    public void init() throws ServletException {
        googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Arrays.asList("714940765820-4ronadar6r8rjgkn0lih986sgi14vl0o.apps.googleusercontent.com"))
                .setIssuer("accounts.google.com")
                .build();
    }

    public String getGoogleId(String token) throws GeneralSecurityException, IOException {
        GoogleIdToken verify = googleIdTokenVerifier.verify(token);
        return verify.getPayload().getSubject();
    }
}
