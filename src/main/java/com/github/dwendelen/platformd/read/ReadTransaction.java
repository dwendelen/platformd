package com.github.dwendelen.platformd.read;


import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Table(name = "account")
public class ReadTransaction {
    @PartitionKey
    @Column(name = "account_uuid")
    private UUID accountUuid;
    @ClusteringColumn(0)
    @Column(name = "transaction_time")
    private Instant timestamp;
    @ClusteringColumn(1)
    @Column(name="transaction_uuid")
    private UUID transactionUuid;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "comment")
    private String comment;
    @Column(name = "balance")
    private BigDecimal accountBalance;

    public UUID getAccountUuid() {
        return accountUuid;
    }

    public ReadTransaction setAccountUuid(UUID accountUuid) {
        this.accountUuid = accountUuid;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ReadTransaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public UUID getTransactionUuid() {
        return transactionUuid;
    }

    public ReadTransaction setTransactionUuid(UUID transactionUuid) {
        this.transactionUuid = transactionUuid;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public ReadTransaction setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Instant getTimestamp() {
        return timestamp;
    }

    public ReadTransaction setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public ReadTransaction setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }
}
