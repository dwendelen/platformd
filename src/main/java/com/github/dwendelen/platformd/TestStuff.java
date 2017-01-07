package com.github.dwendelen.platformd;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

/**
 * Created by xtrit on 1/01/17.
 */
@Component
public class TestStuff {
/*
@RequestMapping(value = "/googlelogin", method = RequestMethod.POST)
    public String login(@RequestBody String token) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Arrays.asList("714940765820-4ronadar6r8rjgkn0lih986sgi14vl0o.apps.googleusercontent.com"))
                .setIssuer("accounts.google.com")
                .build();

        try {
            GoogleIdToken verify = verifier.verify(token);
            String myId = verify.getPayload().getSubject();
            System.out.println(myId);
            return myId;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return "Failed";
        } catch (IOException e) {
            e.printStackTrace();
            return "IOE";
        } catch (Exception e) {
            e.printStackTrace();
            return "E";
        }

    }
 */
}
