package com.github.dwendelen.platformd.read;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "budget_item")
public class ReadBudgetItem {
    @PartitionKey
    @Column(name = "uuid")
    public String uuid;
    @Column(name = "name")
    public String name;
}