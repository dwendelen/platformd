package com.github.dwendelen.platformd.rest.domain.account;


import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "transaction")
public class Transaction {
    @PartitionKey
    @Column(name = "account_id")
    private UUID accountId;
    @ClusteringColumn(0)
    @Column(name = "transaction_date")
    private LocalDate transactionDate;
    @ClusteringColumn(1)
    @Column(name="transaction_id")
    private UUID transactionId;
    @Column(name = "budget_item")
    private UUID budgetItem;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "comment")
    private String comment;

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

    public UUID getTransactionId() {
        return transactionId;
    }

    public Transaction setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
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
    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Transaction setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }
}
