package com.github.dwendelen.platformd.core.budget;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import com.github.dwendelen.platformd.core.budget.event.BudgetItemCreated;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class ReadBudgetItemDao {
    @Autowired
    private Session session;

    public List<ReadBudgetItem> getBudgetItems() {
        ResultSet results = session.execute(QueryBuilder.select().from("budget_item"));
        return StreamSupport.stream(results.spliterator(), false)
                .map(row -> {
                    ReadBudgetItem readAccount = new ReadBudgetItem();
                    readAccount.name = row.getString("name");
                    readAccount.uuid = row.getUUID("uuid").toString();
                    return readAccount;
                })
                .collect(Collectors.toList());
    }

    @EventHandler
    public void on(BudgetItemCreated budgetItemCreated) {
        session.execute(QueryBuilder.insertInto("budget_item")
                .value("uuid", budgetItemCreated.uuid)
                .value("name", budgetItemCreated.name));
    }
}
