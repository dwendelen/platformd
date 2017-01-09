package com.github.dwendelen.platformd.core.account.command;

import com.datastax.driver.core.utils.UUIDs;
import com.github.dwendelen.platformd.core.validation.ValidAmount;
import com.github.dwendelen.platformd.core.validation.ValidDate;
import com.github.dwendelen.platformd.core.validation.ValidInstant;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class MakeMoney {
    @TargetAggregateIdentifier
    @NotNull
    private UUID accountId;
    @NotNull
    private UUID transactionId = UUIDs.timeBased();
    @ValidDate
    private LocalDate transactionDate;
    @ValidAmount
    private BigDecimal amount;
    @NotNull
    private UUID incomeSource;
    private String comment;

    public UUID getAccountId() {
        return accountId;
    }

    public MakeMoney setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public MakeMoney setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public MakeMoney setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public MakeMoney setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public UUID getIncomeSource() {
        return incomeSource;
    }

    public MakeMoney setIncomeSource(UUID incomeSource) {
        this.incomeSource = incomeSource;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public MakeMoney setComment(String comment) {
        this.comment = comment;
        return this;
    }
}