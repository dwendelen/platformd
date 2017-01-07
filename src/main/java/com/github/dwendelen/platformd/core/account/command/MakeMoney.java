package com.github.dwendelen.platformd.core.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

public class MakeMoney {
    @TargetAggregateIdentifier
    public UUID accountId;

    public UUID transactionId;
    public BigDecimal amount;
}
