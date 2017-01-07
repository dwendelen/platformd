package com.github.dwendelen.platformd.core.account.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class MoneyMade {
    private UUID accountId;
    private UUID transactionId;
    private Instant transactionDate;
    private BigDecimal amount;
    private String comment;
    private BigDecimal newBalance;

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

    public BigDecimal getNewBalance() {
        return newBalance;
    }

    public MoneyMade setNewBalance(BigDecimal newBalance) {
        this.newBalance = newBalance;
        return this;
    }
}
