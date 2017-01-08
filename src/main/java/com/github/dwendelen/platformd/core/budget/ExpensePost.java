package com.github.dwendelen.platformd.core.budget;

import com.github.dwendelen.platformd.core.budget.command.CreateExpensePost;
import com.github.dwendelen.platformd.core.budget.command.CreateIncomeSource;
import com.github.dwendelen.platformd.core.budget.event.ExpensePostCreated;
import com.github.dwendelen.platformd.core.budget.event.IncomeSourceCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class ExpensePost {
    @AggregateIdentifier
    private UUID uuid;

    @CommandHandler
    public ExpensePost(CreateExpensePost command) {
        apply(new ExpensePostCreated()
            .setUuid(command.getUuid())
            .setName(command.getName())
        );
    }

    @EventSourcingHandler
    public void on(ExpensePostCreated event) {
        this.uuid = event.getUuid();
    }
}
