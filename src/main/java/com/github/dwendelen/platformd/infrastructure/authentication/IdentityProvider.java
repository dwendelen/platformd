package com.github.dwendelen.platformd.infrastructure.authentication;

import com.github.dwendelen.platformd.core.user.User;
import org.springframework.stereotype.Component;

@Component
public class IdentityProvider {
    private ThreadLocal<User> currentUser = new ThreadLocal<>();

    public User getCurrentUser() {
        User user = currentUser.get();
        if(user == null) {
            throw new UnsupportedOperationException("Decent exception");
        }
        return user;
    }

    public void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public void clearCurrentUser() {
        currentUser.remove();
    }
}
