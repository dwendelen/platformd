package com.github.dwendelen.platformd.core.account;

import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import com.github.dwendelen.platformd.core.account.command.MakeMoney;
import com.github.dwendelen.platformd.core.account.event.MoneyMade;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.spi.ValidationProvider;
import java.math.BigDecimal;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class NormalAccount {
    @AggregateIdentifier
    private UUID uuid;
    private BigDecimal balance = BigDecimal.ZERO;

    public NormalAccount() {}

    @CommandHandler
    public NormalAccount(CreateNormalAccount command) {
        NormalAccountCreated normalAccountCreated = new NormalAccountCreated();
        normalAccountCreated.uuid = command.getUuid();
        normalAccountCreated.name = command.getName();

        apply(normalAccountCreated);
    }

    @CommandHandler
    public void handle(MakeMoney makeMoney) {
        BigDecimal newBalance = balance.add(makeMoney.getAmount());

        MoneyMade moneyMade = new MoneyMade()
                .setAccountId(makeMoney.getAccountId())
                .setTransactionId(makeMoney.getTransactionId())
                .setTransactionDate(makeMoney.getTransactionDate())
                .setAmount(makeMoney.getAmount())
                .setComment(makeMoney.getComment())
                .setNewBalance(newBalance);
        apply(moneyMade);
    }

    @EventSourcingHandler
    public void on(NormalAccountCreated normalAccountCreated) {
        this.uuid = normalAccountCreated.uuid;
    }

    @EventSourcingHandler
    public void on(MoneyMade moneyMade) {
        this.balance = moneyMade.getNewBalance();
    }
}
