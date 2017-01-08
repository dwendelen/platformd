package com.github.dwendelen.platformd.rest.domain.user;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(name = "user")
public class User {
    @PartitionKey
    @Column(name = "google_id")
    private String googleId;
    @Column(name = "admin")
    private boolean isAdmin;
    @Column(name = "user_id")
    private UUID userId;

    public String getGoogleId() {
        return googleId;
    }

    public User setGoogleId(String googleId) {
        this.googleId = googleId;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public User setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    public UUID getUserId() {
        return userId;
    }

    public User setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }
}
