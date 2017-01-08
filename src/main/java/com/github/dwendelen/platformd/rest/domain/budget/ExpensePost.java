package com.github.dwendelen.platformd.rest.domain.budget;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "expense_post")
public class ExpensePost {
    @PartitionKey
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "name")
    private String name;

    public String getUuid() {
        return uuid;
    }

    public ExpensePost setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public ExpensePost setName(String name) {
        this.name = name;
        return this;
    }
}