package com.github.dwendelen.platformd.core.budget.command;

import com.github.dwendelen.platformd.core.validation.ValidAmount;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public class RecordIncome {
    @NotNull
    @TargetAggregateIdentifier
    private UUID incomeSource;
    @NotNull
    private UUID transactionId;
    @ValidAmount
    private BigDecimal amount;
    @NotNull
    private UUID accountId;

    public UUID getIncomeSource() {
        return incomeSource;
    }

    public RecordIncome setIncomeSource(UUID incomeSource) {
        this.incomeSource = incomeSource;
        return this;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public RecordIncome setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public RecordIncome setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public RecordIncome setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }
}
