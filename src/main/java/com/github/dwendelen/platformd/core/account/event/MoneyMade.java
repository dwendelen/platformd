package com.github.dwendelen.platformd.core.account.event;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by xtrit on 7/01/17.
 */
public class MoneyMade {
    private UUID accountId;
    private UUID transactionId;
    private BigDecimal amount;

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
}
