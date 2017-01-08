package com.github.dwendelen.platformd.rest;

import com.github.dwendelen.platformd.core.account.command.MakeMoney;
import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import com.github.dwendelen.platformd.rest.domain.account.Account;
import com.github.dwendelen.platformd.rest.domain.account.AccountDao;
import com.github.dwendelen.platformd.rest.domain.account.Transaction;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/accounts")
public class AccountController {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping(method = RequestMethod.GET)
    public List<Account> getAccounts() {
        return accountDao.getAccounts();
    }

    @RequestMapping(value = "/{account}/transactions", method = RequestMethod.GET)
    public List<Transaction> getTransactions(@PathVariable("account") UUID accountUuid) {
        return accountDao.getTransactions(accountUuid);
    }

    @RequestMapping(value = "/{account}/transactions", method = RequestMethod.POST)
    public void  createTransaction(@PathVariable("account") UUID accountUuid,
                                   @RequestBody Transaction readTransaction) {
        MakeMoney makeMoney = new MakeMoney()
                .setAccountId(accountUuid)
                .setTransactionDate(readTransaction.getTimestamp())
                .setAmount(readTransaction.getAmount())
                .setIncomeSource(readTransaction.getBudgetItem())
                .setComment(readTransaction.getComment());
        commandGateway.sendAndWait(makeMoney);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Account createAccount(@RequestBody CreateAccount createAccount) {
        CreateNormalAccount createNormalAccount = new CreateNormalAccount()
                .setName(createAccount.name);

        UUID uuid = commandGateway.sendAndWait(createNormalAccount);
        return accountDao.getAccount(uuid);
    }

    @RequestMapping(value = "/{account}", method = RequestMethod.DELETE)
    public void deleteAccount(@PathVariable("account") UUID accountUuid) {
        throw new UnsupportedOperationException();
    }

    private static class CreateAccount {
        public String name;
    }
}
