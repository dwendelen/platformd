package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.account.ReadAccount;
import com.github.dwendelen.platformd.core.account.ReadAccountDao;
import com.github.dwendelen.platformd.core.account.ReadTransaction;
import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {
    @Autowired
    private ReadAccountDao readAccountDao;
    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(method = RequestMethod.GET)
    public List<ReadAccount> getAccounts() {
        return readAccountDao.getAccounts();
    }

    @RequestMapping(value = "/{account}/transactions", method = RequestMethod.GET)
    public List<ReadTransaction> getTransactions(@PathVariable("account")UUID accountUuid) {
        return readAccountDao.getTransactions(accountUuid);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createAccount(@RequestBody CreateAccount createAccount) {
        CreateNormalAccount createNormalAccount = new CreateNormalAccount();
        createNormalAccount.name = createAccount.name;
        createNormalAccount.initialBalance = createAccount.initialBalance;

        commandGateway.sendAndWait(createNormalAccount);
    }
    private static class CreateAccount {
        public String name;
        public BigDecimal initialBalance;
    }
}
