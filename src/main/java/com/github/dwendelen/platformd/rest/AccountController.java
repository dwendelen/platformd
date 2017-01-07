package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.account.command.MakeMoney;
import com.github.dwendelen.platformd.read.ReadAccount;
import com.github.dwendelen.platformd.read.ReadAccountDao;
import com.github.dwendelen.platformd.read.ReadTransaction;
import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<ReadTransaction> getTransactions(@PathVariable("account") UUID accountUuid) {
        return readAccountDao.getTransactions(accountUuid);
    }

    @RequestMapping(value = "/{account}/transactions", method = RequestMethod.POST)
public void  createTransaction(@PathVariable("account") UUID accountUuid,
                                         @RequestBody ReadTransaction readTransaction) {
        MakeMoney makeMoney = new MakeMoney()
                .setAccountId(accountUuid)
                .setAmount(readTransaction.getAmount())
                .setComment(readTransaction.getComment())
                .setTransactionDate(readTransaction.getTimestamp());
        commandGateway.sendAndWait(makeMoney);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ReadAccount createAccount(@RequestBody CreateAccount createAccount) {
        CreateNormalAccount createNormalAccount = new CreateNormalAccount()
                .setName(createAccount.name);

        UUID uuid = commandGateway.sendAndWait(createNormalAccount);
        return readAccountDao.getAccount(uuid);
    }

    @RequestMapping(value = "/{account}", method = RequestMethod.DELETE)
    public void deleteAccount(@PathVariable("account") UUID accountUuid) {
        throw new UnsupportedOperationException();
    }

    private static class CreateAccount {
        public String name;
    }
}
