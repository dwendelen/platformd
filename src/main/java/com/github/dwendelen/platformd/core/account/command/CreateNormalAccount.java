package com.github.dwendelen.platformd.core.account.command;

import com.datastax.driver.core.utils.UUIDs;
import com.github.dwendelen.platformd.core.validation.ValidAmount;
import com.github.dwendelen.platformd.core.validation.ValidName;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateNormalAccount {
    @NotNull
    private UUID uuid = UUIDs.timeBased();
    @NotNull
    private UUID owner;
    @ValidName
    private String name;

    public UUID getUuid() {
        return uuid;
    }

    public CreateNormalAccount setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UUID getOwner() {
        return owner;
    }

    public CreateNormalAccount setOwner(UUID owner) {
        this.owner = owner;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateNormalAccount setName(String name) {
        this.name = name;
        return this;
    }
}