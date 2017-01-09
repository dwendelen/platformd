package com.github.dwendelen.platformd.core.budget.event;

import com.github.dwendelen.platformd.core.budget.command.CreateExpensePost;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class IncomeSourceCreated {
    @TargetAggregateIdentifier
    private UUID uuid;
    private UUID owner;
    private String name;

    public UUID getUuid() {
        return uuid;
    }

    public IncomeSourceCreated setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UUID getOwner() {
        return owner;
    }

    public IncomeSourceCreated setOwner(UUID owner) {
        this.owner = owner;
        return this;
    }

    public String getName() {
        return name;
    }

    public IncomeSourceCreated setName(String name) {
        this.name = name;
        return this;
    }
}
