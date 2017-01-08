package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.rest.domain.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    @Autowired
    private UserDao userDao;


}
