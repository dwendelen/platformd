package com.github.dwendelen.platformd.core.transaction;

import com.github.dwendelen.platformd.core.account.command.AcceptMoneyMade;
import com.github.dwendelen.platformd.core.account.command.RejectMoneyMade;
import com.github.dwendelen.platformd.core.account.event.MoneyMadeInitiated;
import com.github.dwendelen.platformd.core.budget.command.RecordIncome;
import com.github.dwendelen.platformd.core.budget.event.IncomeRecorded;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
public class AccountBudget {
    @Autowired
    private CommandGateway gateway;

    @EventHandler
    public void on(MoneyMadeInitiated event) {
        try {
            gateway.sendAndWait(new RecordIncome()
                    .setTransactionId(event.getTransactionId())
                    .setIncomeSource(event.getIncomeSource())
                    .setAmount(event.getAmount())
                    .setAccountId(event.getAccountId())
            );
        } catch (Exception e) {
            gateway.sendAndWait(new RejectMoneyMade()
                    .setAccountId(event.getAccountId())
                    .setTransactionId(event.getTransactionId())
                    .setReason(e.getMessage())
            );
        }
    }

    @EventHandler
    public void on(IncomeRecorded event) {
        gateway.sendAndWait(new AcceptMoneyMade()
                .setAccountId(event.getAccountId())
                .setTransactionId(event.getTransactionId())
        );
    }
}
