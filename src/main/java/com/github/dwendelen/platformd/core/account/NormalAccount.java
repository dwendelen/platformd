package com.github.dwendelen.platformd.core.account;

import com.github.dwendelen.platformd.core.account.command.AcceptMoneyMade;
import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import com.github.dwendelen.platformd.core.account.command.MakeMoney;
import com.github.dwendelen.platformd.core.account.command.RejectMoneyMade;
import com.github.dwendelen.platformd.core.account.event.MoneyMade;
import com.github.dwendelen.platformd.core.account.event.MoneyMadeInitiated;
import com.github.dwendelen.platformd.core.account.event.MoneyMadeRejected;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class NormalAccount {
    @AggregateIdentifier
    private UUID uuid;
    private BigDecimal balance = BigDecimal.ZERO;

    private Map<UUID, Transaction> ongoingTransactions = new HashMap<>();

    public NormalAccount() {}

    @CommandHandler
    public NormalAccount(CreateNormalAccount command) {
        NormalAccountCreated normalAccountCreated = new NormalAccountCreated();
        normalAccountCreated.uuid = command.getUuid();
        normalAccountCreated.name = command.getName();

        apply(normalAccountCreated);
    }

    @EventSourcingHandler
    public void on(NormalAccountCreated normalAccountCreated) {
        this.uuid = normalAccountCreated.uuid;
    }

    @CommandHandler
    public void handle(MakeMoney makeMoney) {
        MoneyMadeInitiated moneyMadeInitiated = new MoneyMadeInitiated()
                .setAccountId(makeMoney.getAccountId())
                .setTransactionId(makeMoney.getTransactionId())
                .setTransactionDate(makeMoney.getTransactionDate())
                .setAmount(makeMoney.getAmount())
                .setIncomeSource(makeMoney.getIncomeSource())
                .setComment(makeMoney.getComment());
        apply(moneyMadeInitiated);
    }

    @EventSourcingHandler
    public void on(MoneyMadeInitiated event) {
        Transaction transaction = new Transaction()
                .setTransactionDate(event.getTransactionDate())
                .setAmount(event.getAmount())
                .setDestination(event.getIncomeSource())
                .setComment(event.getComment());
        ongoingTransactions.put(event.getTransactionId(), transaction);
    }

    @CommandHandler
    public void handle(AcceptMoneyMade acceptMoneyMade) {
        Transaction transaction = ongoingTransactions.get(acceptMoneyMade.getTransactionId());
        BigDecimal newBalance = balance.add(transaction.getAmount());

        apply(new MoneyMade()
                .setAccountId(acceptMoneyMade.getAccountId())
                .setTransactionId(acceptMoneyMade.getTransactionId())
                .setTransactionDate(transaction.getTransactionDate())
                .setAmount(transaction.getAmount())
                .setNewBalance(newBalance)
                .setIncomeSource(transaction.getDestination())
                .setComment(transaction.getComment())
        );
    }

    @EventSourcingHandler
    public void on(MoneyMade moneyMade) {
        ongoingTransactions.remove(moneyMade.getTransactionId());
        balance = moneyMade.getNewBalance();
    }

    @CommandHandler
    public void handle(RejectMoneyMade rejectMoneyMade) {
        apply(new MoneyMadeRejected()
                .setAccountId(rejectMoneyMade.getAccountId())
                .setTransactionId(rejectMoneyMade.getTransactionId())
                .setReason(rejectMoneyMade.getReason())
        );
    }

    @EventSourcingHandler
    public void on(MoneyMadeRejected moneyMadeRejected) {
        ongoingTransactions.remove(moneyMadeRejected.getTransactionId());
    }
}