package com.github.dwendelen.platformd.core.budget;

import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import com.github.dwendelen.platformd.core.budget.command.CreateBudgetItem;
import com.github.dwendelen.platformd.core.budget.event.BudgetItemCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class BudgetItem {
    @AggregateIdentifier
    private UUID uuid;

    public BudgetItem() {}

    @CommandHandler
    public BudgetItem(CreateBudgetItem command) {
        BudgetItemCreated budgetItemCreated = new BudgetItemCreated();
        budgetItemCreated.uuid = command.getUuid();
        budgetItemCreated.name = command.getName();

        apply(budgetItemCreated);
    }

    @EventSourcingHandler
    public void on(BudgetItemCreated budgetItemCreated) {
        this.uuid = budgetItemCreated.uuid;
    }
}
