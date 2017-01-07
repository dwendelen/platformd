package com.github.dwendelen.platformd.core.account.command;

import com.github.dwendelen.platformd.core.validation.ValidAmount;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class MakeMoney {
    @TargetAggregateIdentifier
    @NotNull
    public UUID accountId;

    @NotNull
    public UUID transactionId;

    @ValidAmount
    public BigDecimal amount;
}