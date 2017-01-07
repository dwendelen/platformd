package com.github.dwendelen.platformd.read;

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
public class ReadAccountDao {
    private final Mapper<ReadAccount> accountMapper;
    private final Mapper<ReadTransaction> transactionMapper;
    private AccountAccessor accountAccessor;

    @Autowired
    public ReadAccountDao(MappingManager mappingManager) {
        this.accountMapper = mappingManager.mapper(ReadAccount.class);
        this.transactionMapper = mappingManager.mapper(ReadTransaction.class);
        this.accountAccessor = mappingManager.createAccessor(AccountAccessor.class);
    }

    public List<ReadAccount> getAccounts() {
        return accountAccessor.getAllAccounts().all();
    }

    public ReadAccount getAccount(UUID uuid) {
        return accountMapper.get(uuid);
    }

    @EventHandler
    public void on(NormalAccountCreated normalAccountCreated) {
        ReadAccount newAccount = new ReadAccount()
                .setUuid(normalAccountCreated.uuid)
                .setName(normalAccountCreated.name)
                .setBalance(BigDecimal.ZERO);
        accountMapper.save(newAccount);
    }

    public List<ReadTransaction> getTransactions(UUID account) {
        return accountAccessor.getAllTransactions(account).all().stream()
                .filter(t -> t.getTransactionUuid() != null)
                .collect(Collectors.toList());
    }

    @EventHandler
    public void on(MoneyMade moneyMade) {
        ReadTransaction newTransaction = new ReadTransaction()
                .setAccountUuid(moneyMade.getAccountId())
                .setTransactionUuid(moneyMade.getTransactionId())
                .setTimestamp(moneyMade.getTransactionDate())
                .setAmount(moneyMade.getAmount())
                .setComment(moneyMade.getComment())
                .setAccountBalance(moneyMade.getNewBalance());

        transactionMapper.save(newTransaction);
    }

    @Accessor
    public interface AccountAccessor {
        @Query("SELECT DISTINCT account_uuid, account_name, balance FROM account")
        Result<ReadAccount> getAllAccounts();

        @Query("SELECT * FROM account WHERE account_uuid=:arg0")
        Result<ReadTransaction> getAllTransactions(UUID account);
    }
}
