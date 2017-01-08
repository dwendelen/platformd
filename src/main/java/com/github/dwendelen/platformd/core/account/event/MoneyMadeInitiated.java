package com.github.dwendelen.platformd.core.account.event;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class MoneyMadeInitiated {
    @TargetAggregateIdentifier
    private UUID accountId;
    private UUID transactionId;
    private Instant transactionDate;
    private BigDecimal amount;
    private UUID incomeSource;
    private String comment;

    public UUID getAccountId() {
        return accountId;
    }

    public MoneyMadeInitiated setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public MoneyMadeInitiated setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public MoneyMadeInitiated setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public UUID getIncomeSource() {
        return incomeSource;
    }

    public MoneyMadeInitiated setIncomeSource(UUID incomeSource) {
        this.incomeSource = incomeSource;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public MoneyMadeInitiated setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public MoneyMadeInitiated setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }
}