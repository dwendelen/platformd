package com.github.dwendelen.platformd.infrastructure.authentication

import java.util
import javax.annotation.PostConstruct

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

//Daans google id: 110479258309610389601
@Component
class GoogleTokenService(@Value("platformd.google.clientId") clientId: String) {
    private var googleIdTokenVerifier: GoogleIdTokenVerifier = _

    @PostConstruct
    def init() {
        googleIdTokenVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport, new JacksonFactory)
                .setAudience(util.Arrays.asList(clientId))
                .setIssuer("accounts.google.com")
                .build
    }

    def getGoogleId(token: String): String = {
        val verify = googleIdTokenVerifier.verify(token)
        verify.getPayload.getSubject
    }
}
