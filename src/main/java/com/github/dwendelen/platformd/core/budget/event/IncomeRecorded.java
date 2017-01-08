package com.github.dwendelen.platformd.core.budget.event;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;


public class IncomeRecorded {
    @TargetAggregateIdentifier
    private UUID incomeSource;
    private UUID transactionId;
    private BigDecimal amount;
    private UUID accountId;

    public UUID getIncomeSource() {
        return incomeSource;
    }

    public IncomeRecorded setIncomeSource(UUID incomeSource) {
        this.incomeSource = incomeSource;
        return this;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public IncomeRecorded setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public IncomeRecorded setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public IncomeRecorded setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }
}
