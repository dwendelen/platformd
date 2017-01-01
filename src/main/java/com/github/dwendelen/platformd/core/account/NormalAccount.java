package com.github.dwendelen.platformd.core.account;

import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import com.github.dwendelen.platformd.core.account.command.MakeMoney;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class NormalAccount {
    @AggregateIdentifier
    private UUID uuid;

    public NormalAccount() {}

    @CommandHandler
    public NormalAccount(CreateNormalAccount command) {
        NormalAccountCreated normalAccountCreated = new NormalAccountCreated();
        normalAccountCreated.uuid = command.uuid;
        normalAccountCreated.name = command.name;

        apply(normalAccountCreated);
    }

    @EventSourcingHandler
    public void on(NormalAccountCreated normalAccountCreated) {
        this.uuid = normalAccountCreated.uuid;
    }
}
