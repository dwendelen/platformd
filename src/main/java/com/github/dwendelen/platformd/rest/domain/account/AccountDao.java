package com.github.dwendelen.platformd.rest.domain.account;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.github.dwendelen.platformd.core.account.event.MoneyMade;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import com.github.dwendelen.platformd.infrastructure.authentication.IdentityProvider;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/*
CREATE TABLE account (
	user_id timeuuid,
	account_id timeuuid,
	account_name text,
	balance decimal,
	primary key (user_id, account_id)
);

CREATE TABLE transaction (
	account_id timeuuid,
	transaction_date date,
	transaction_id timeuuid,
	amount decimal,
    budget_item timeuuid,
	comment text,
	primary key (account_id, transaction_date, transaction_id)
) WITH CLUSTERING ORDER BY (transaction_date DESC, transaction_id DESC);

 */
@Component
public class AccountDao {
    private final Mapper<Account> accountMapper;
    private final Mapper<Transaction> transactionMapper;
    private AccountAccessor accountAccessor;
    private IdentityProvider identityProvider;

    @Autowired
    public AccountDao(MappingManager mappingManager, IdentityProvider identityProvider) {
        this.accountMapper = mappingManager.mapper(Account.class);
        this.transactionMapper = mappingManager.mapper(Transaction.class);
        this.accountAccessor = mappingManager.createAccessor(AccountAccessor.class);
        this.identityProvider = identityProvider;
    }

    public List<Account> getAccounts(UUID userId) {
        return accountAccessor.getAccounts(userId).all();
    }

    public Account getAccount(UUID userId, UUID uuid) {
        return accountMapper.get(userId, uuid);
    }

    @EventHandler
    public void on(NormalAccountCreated normalAccountCreated) {
        Account newAccount = new Account()
                .setUserId(normalAccountCreated.owner)
                .setAccountId(normalAccountCreated.uuid)
                .setName(normalAccountCreated.name)
                .setBalance(BigDecimal.ZERO);
        accountMapper.save(newAccount);
    }

    public List<Transaction> getTransactions(UUID account) {
        return accountAccessor.getTransactions(account).all();
    }

    @EventHandler
    public void on(MoneyMade event) {
        Account account = accountMapper.get(identityProvider.getCurrentUser().getUserId(),event.getAccountId());
        account.setBalance(event.getNewBalance());
        accountMapper.save(account);

        Transaction newTransaction = new Transaction()
                .setAccountId(event.getAccountId())
                .setTransactionId(event.getTransactionId())
                .setTransactionDate(event.getTransactionDate())
                .setAmount(event.getAmount())
                .setComment(event.getComment());
        transactionMapper.save(newTransaction);
    }

    @Accessor
    private interface AccountAccessor {
        @Query("SELECT * FROM transaction WHERE account_id=:arg0")
        Result<Transaction> getTransactions(UUID accountId);

        @Query("SELECT * FROM account WHERE user_id=:arg0")
        Result<Account> getAccounts(UUID userId);
    }
}