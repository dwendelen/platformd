package com.github.dwendelen.platformd.rest.domain.account;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;


import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "account")
public class Account {
    @PartitionKey
    @Column(name = "user_id")
    private UUID userId;

    @ClusteringColumn
    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "account_name")
    private String name;

    @Column(name = "balance")
    private BigDecimal balance;

    public UUID getUserId() {
        return userId;
    }

    public Account setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public Account setAccountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }
}
