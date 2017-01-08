package com.github.dwendelen.platformd.core.account.event;

import com.github.dwendelen.platformd.core.account.command.RejectMoneyMade;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class MoneyMadeRejected {
    @TargetAggregateIdentifier
    private UUID accountId;
    private UUID transactionId;
    private String reason;

    public UUID getAccountId() {
        return accountId;
    }

    public MoneyMadeRejected setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public MoneyMadeRejected setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public MoneyMadeRejected setReason(String reason) {
        this.reason = reason;
        return this;
    }
}