package com.github.dwendelen.platformd.core.budget.command;

import com.github.dwendelen.platformd.core.validation.ValidName;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateBudgetItem {
    @NotNull
    private UUID uuid = UUID.randomUUID();
    @ValidName
    private String name;

    public UUID getUuid() {
        return uuid;
    }

    public CreateBudgetItem setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateBudgetItem setName(String name) {
        this.name = name;
        return this;
    }
}
