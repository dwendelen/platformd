package com.github.dwendelen.platformd.core.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RejectMoneyMade {
    @TargetAggregateIdentifier
    @NotNull
    private UUID accountId;
    @NotNull
    private UUID transactionId;
    @NotNull
    private String reason;

    public UUID getAccountId() {
        return accountId;
    }

    public RejectMoneyMade setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public RejectMoneyMade setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RejectMoneyMade setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
