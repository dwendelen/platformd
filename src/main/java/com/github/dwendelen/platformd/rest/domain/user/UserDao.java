package com.github.dwendelen.platformd.rest.domain.user;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {
    private Mapper<User> mapper;

    @Autowired
    public UserDao(MappingManager mappingManager) {
        this.mapper = mappingManager.mapper(User.class);
    }

    public User get(String googleId) {
        return mapper.get(googleId);
    }
}
