package com.github.dwendelen.platformd.core.budget;

import com.github.dwendelen.platformd.core.budget.command.CreateExpensePost;
import com.github.dwendelen.platformd.core.budget.command.CreateIncomeSource;
import com.github.dwendelen.platformd.core.budget.command.RecordIncome;
import com.github.dwendelen.platformd.core.budget.event.ExpensePostCreated;
import com.github.dwendelen.platformd.core.budget.event.IncomeRecorded;
import com.github.dwendelen.platformd.core.budget.event.IncomeSourceCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class IncomeSource {
    @AggregateIdentifier
    private UUID uuid;

    public IncomeSource() {}

    @CommandHandler
    public IncomeSource(CreateIncomeSource command) {
        apply(new IncomeSourceCreated()
                .setUuid(command.getUuid())
                .setName(command.getName())
        );
    }

    @EventSourcingHandler
    public void on(IncomeSourceCreated event) {
        this.uuid = event.getUuid();
    }

    @CommandHandler
    public void handle(RecordIncome command) {
        apply(new IncomeRecorded()
                .setIncomeSource(command.getIncomeSource())
                .setAmount(command.getAmount())
                .setAccountId(command.getAccountId())
                .setTransactionId(command.getTransactionId())
        );
    }

    @EventSourcingHandler
    public void on(IncomeRecorded event) {

    }
}