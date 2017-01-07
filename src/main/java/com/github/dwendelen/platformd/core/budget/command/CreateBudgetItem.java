package com.github.dwendelen.platformd.core.budget.command;

import com.github.dwendelen.platformd.core.validation.ValidName;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateBudgetItem {
    @NotNull
    public UUID uuid = UUID.randomUUID();
    @ValidName
    public String name;
}
