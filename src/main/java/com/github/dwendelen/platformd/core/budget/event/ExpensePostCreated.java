package com.github.dwendelen.platformd.core.budget.event;

import com.github.dwendelen.platformd.core.budget.command.CreateExpensePost;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class ExpensePostCreated {
    @TargetAggregateIdentifier
    private UUID uuid;
    private String name;

    public UUID getUuid() {
        return uuid;
    }

    public ExpensePostCreated setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public ExpensePostCreated setName(String name) {
        this.name = name;
        return this;
    }
}
