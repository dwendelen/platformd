package com.github.dwendelen.platformd.core.budget.command;

import com.datastax.driver.core.utils.UUIDs;
import com.github.dwendelen.platformd.core.validation.ValidName;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateIncomeSource {
    @NotNull
    private UUID uuid = UUIDs.timeBased();
    @NotNull
    private UUID owner;
    @ValidName
    private String name;

    public UUID getUuid() {
        return uuid;
    }

    public CreateIncomeSource setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UUID getOwner() {
        return owner;
    }

    public CreateIncomeSource setOwner(UUID owner) {
        this.owner = owner;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateIncomeSource setName(String name) {
        this.name = name;
        return this;
    }
}
