package com.github.dwendelen.platformd.core.account;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.github.dwendelen.platformd.core.account.event.MoneyMade;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class ReadAccountDao {
    @Autowired
    private Session session;

    public List<ReadAccount> getAccounts() {
        ResultSet results = session.execute(QueryBuilder.select().from("account"));
        return StreamSupport.stream(results.spliterator(), false)
                .map(row -> {
                    ReadAccount readAccount = new ReadAccount();
                    readAccount.name = row.getString("account_name");
                    readAccount.uuid = row.getUUID("account_uuid").toString();
                    return readAccount;
                })
                .collect(Collectors.toList());
    }

    @EventHandler
    public void on(NormalAccountCreated normalAccountCreated) {
        session.execute(QueryBuilder.insertInto("account")
                .value("account_uuid", normalAccountCreated.uuid)
                .value("account_name", normalAccountCreated.name));
    }

    public List<ReadTransaction> getTransactions(UUID account) {
        ResultSet results = session.execute(QueryBuilder.select().from("account")
                .where(QueryBuilder.eq("account_uuid", account)));
        return StreamSupport.stream(results.spliterator(), false)
                .map(row -> new ReadTransaction()
                        .setAccountUuid(row.getUUID("account_uuid"))
                        .setTransactionUuid(row.getUUID("transaction_uuid"))
                        .setAmount(row.getDecimal("amount"))
                )
                .filter(t -> t.getTransactionUuid() != null)
                .collect(Collectors.toList());
    }

    @EventHandler
    public void on(MoneyMade moneyMade) {
        session.execute(QueryBuilder.insertInto("account")
                .value("account_uuid", moneyMade.getAccountId())
                .value("transaction_uuid", moneyMade.getTransactionId())
                .value("amount", moneyMade.getAmount()));
    }
}
