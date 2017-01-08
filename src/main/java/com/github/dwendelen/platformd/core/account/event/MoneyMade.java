package com.github.dwendelen.platformd.core.account.event;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class MoneyMade {
    @TargetAggregateIdentifier
    private UUID accountId;
    private UUID transactionId;
    private Instant transactionDate;
    private BigDecimal amount;
    private BigDecimal newBalance;
    private UUID incomeSource;
    private String comment;

    public UUID getAccountId() {
        return accountId;
    }

    public MoneyMade setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public MoneyMade setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public MoneyMade setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public MoneyMade setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
        return this;
    }

    public UUID getIncomeSource() {
        return incomeSource;
    }

    public MoneyMade setIncomeSource(UUID incomeSource) {
        this.incomeSource = incomeSource;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public MoneyMade setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public MoneyMade setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }
}