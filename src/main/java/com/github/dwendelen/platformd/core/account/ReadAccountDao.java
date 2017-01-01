package com.github.dwendelen.platformd.core.account;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
                    readAccount.name = row.getString("name");
                    readAccount.uuid = row.getUUID("uuid").toString();
                    return readAccount;
                })
                .collect(Collectors.toList());
    }

    @EventHandler
    public void on(NormalAccountCreated normalAccountCreated) {
        session.execute(QueryBuilder.insertInto("account")
                .value("uuid", normalAccountCreated.uuid)
                .value("name", normalAccountCreated.name));
    }
}
