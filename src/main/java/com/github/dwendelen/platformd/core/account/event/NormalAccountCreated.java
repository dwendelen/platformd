package com.github.dwendelen.platformd.core.account.event;

import com.github.dwendelen.platformd.core.account.NormalAccount;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.UUID;

public class NormalAccountCreated {
    @TargetAggregateIdentifier
    public UUID uuid;
    public String name;
}
