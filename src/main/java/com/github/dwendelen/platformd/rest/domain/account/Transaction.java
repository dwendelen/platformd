package com.github.dwendelen.platformd.rest.domain.account;


import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(name = "account")
public class Transaction {
    @PartitionKey
    @Column(name = "account_uuid")
    private UUID accountId;
    @ClusteringColumn(0)
    @Column(name = "transaction_time")
    private Instant timestamp;
    @ClusteringColumn(1)
    @Column(name="transaction_uuid")
    private UUID transactionUuid;
    @Column(name = "budget_item")
    private UUID budgetItem;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "comment")
    private String comment;
    @Column(name = "balance")
    private BigDecimal accountBalance;

    public UUID getAccountId() {
        return accountId;
    }

    public Transaction setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public UUID getTransactionUuid() {
        return transactionUuid;
    }

    public Transaction setTransactionUuid(UUID transactionUuid) {
        this.transactionUuid = transactionUuid;
        return this;
    }

    public UUID getBudgetItem() {
        return budgetItem;
    }

    public Transaction setBudgetItem(UUID budgetItem) {
        this.budgetItem = budgetItem;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Transaction setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Instant getTimestamp() {
        return timestamp;
    }

    public Transaction setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public Transaction setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }
}
