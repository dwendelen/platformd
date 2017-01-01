package com.github.dwendelen.platformd.core.account.command;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class MakeMoney {
    @TargetAggregateIdentifier
    public UUID uuid = UUID.randomUUID();
    public int amount;
}
