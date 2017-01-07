package com.github.dwendelen.platformd.core.account;


import java.math.BigDecimal;
import java.util.UUID;

public class ReadTransaction {
    private UUID accountUuid;
    private UUID transactionUuid;
    private BigDecimal amount;

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
}
