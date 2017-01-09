package com.github.dwendelen.platformd.rest.domain.budget;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(name = "income_source")
public class IncomeSource {
    @PartitionKey()
    @Column(name = "user_id")
    private UUID userId;
    @ClusteringColumn
    @Column(name = "income_source_id")
    private UUID uuid;
    @Column(name = "name")
    private String name;

    public UUID getUserId() {
        return userId;
    }

    public IncomeSource setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public UUID getUuid() {
        return uuid;
    }

    public IncomeSource setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public IncomeSource setName(String name) {
        this.name = name;
        return this;
    }
}
