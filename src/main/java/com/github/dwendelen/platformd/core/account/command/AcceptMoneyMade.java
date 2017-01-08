package com.github.dwendelen.platformd.core.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AcceptMoneyMade {
    @TargetAggregateIdentifier
    @NotNull
    private UUID accountId;
    @NotNull
    private UUID transactionId;

    public UUID getAccountId() {
        return accountId;
    }

    public AcceptMoneyMade setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public AcceptMoneyMade setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }
}