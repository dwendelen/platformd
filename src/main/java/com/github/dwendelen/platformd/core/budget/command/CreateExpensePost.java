package com.github.dwendelen.platformd.core.budget.command;

import com.datastax.driver.core.utils.UUIDs;

import java.util.UUID;

public class CreateExpensePost {
    private UUID uuid = UUIDs.timeBased();
    private String name;

    public UUID getUuid() {
        return uuid;
    }

    public CreateExpensePost setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateExpensePost setName(String name) {
        this.name = name;
        return this;
    }
}
