package com.github.dwendelen.platformd.core.account;


import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;
import java.util.UUID;

public class ReadTransaction {
    private UUID accountUuid;
    private UUID transactionUuid;
    private BigDecimal amount;
    private String comment;

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
        return Instant.ofEpochMilli(UUIDs.unixTimestamp(transactionUuid));
    }
}
