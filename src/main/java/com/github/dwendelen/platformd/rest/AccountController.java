package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.account.ReadAccount;
import com.github.dwendelen.platformd.core.account.ReadAccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {
    @Autowired
    private ReadAccountDao readAccountDao;

    @RequestMapping(method = RequestMethod.GET)
    public List<ReadAccount> getAccounts() {
    return readAccountDao.getAccounts();
    }
}
