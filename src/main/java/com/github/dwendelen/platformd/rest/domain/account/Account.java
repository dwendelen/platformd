package com.github.dwendelen.platformd.rest.domain.account;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;


import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "account")
public class Account {
    @PartitionKey
    @Column(name = "account_uuid")
    private UUID uuid;

    @Column(name = "account_name")
    private String name;

    @Column(name = "balance")
    private BigDecimal balance;

    public UUID getUuid() {
        return uuid;
    }

    public Account setUuid(UUID uuid) {
        this.uuid = uuid;
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
