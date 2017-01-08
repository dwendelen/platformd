package com.github.dwendelen.platformd.rest.domain.account;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.github.dwendelen.platformd.core.account.event.MoneyMade;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class AccountDao {
    private final Mapper<Account> accountMapper;
    private final Mapper<Transaction> transactionMapper;
    private AccountAccessor accountAccessor;

    @Autowired
    public AccountDao(MappingManager mappingManager) {
        this.accountMapper = mappingManager.mapper(Account.class);
        this.transactionMapper = mappingManager.mapper(Transaction.class);
        this.accountAccessor = mappingManager.createAccessor(AccountAccessor.class);
    }

    public List<Account> getAccounts() {
        return accountAccessor.getAllAccounts().all();
    }

    public Account getAccount(UUID uuid) {
        return accountMapper.get(uuid);
    }

    @EventHandler
    public void on(NormalAccountCreated normalAccountCreated) {
        Account newAccount = new Account()
                .setUuid(normalAccountCreated.uuid)
                .setName(normalAccountCreated.name)
                .setBalance(BigDecimal.ZERO);
        accountMapper.save(newAccount);
    }

    public List<Transaction> getTransactions(UUID account) {
        return accountAccessor.getAllTransactions(account).all().stream()
                .filter(t -> t.getTransactionUuid() != null)
                .collect(Collectors.toList());
    }

    @EventHandler
    public void on(MoneyMade event) {
        Transaction newTransaction = new Transaction()
                .setAccountId(event.getAccountId())
                .setTransactionUuid(event.getTransactionId())
                .setTimestamp(event.getTransactionDate())
                .setAmount(event.getAmount())
                .setComment(event.getComment())
                .setAccountBalance(event.getNewBalance());

        transactionMapper.save(newTransaction);
    }

    @Accessor
    public interface AccountAccessor {
        @Query("SELECT DISTINCT account_uuid, account_name, balance FROM account")
        Result<Account> getAllAccounts();

        @Query("SELECT * FROM account WHERE account_uuid=:arg0")
        Result<Transaction> getAllTransactions(UUID account);
    }
}
