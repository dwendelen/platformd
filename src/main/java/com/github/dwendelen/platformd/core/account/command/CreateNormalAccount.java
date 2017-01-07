package com.github.dwendelen.platformd.core.account.command;

import java.math.BigDecimal;
import java.util.UUID;

public class CreateNormalAccount {
    public UUID uuid = UUID.randomUUID();
    public String name;
    public BigDecimal initialBalance;
}
