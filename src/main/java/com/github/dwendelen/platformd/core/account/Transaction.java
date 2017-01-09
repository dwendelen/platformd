package com.github.dwendelen.platformd.core.account;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private UUID destination;
    private LocalDate transactionDate;
    private BigDecimal amount;
    private String comment;

    public UUID getDestination() {
        return destination;
    }

    public Transaction setDestination(UUID destination) {
        this.destination = destination;
        return this;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Transaction setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Transaction setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }
}