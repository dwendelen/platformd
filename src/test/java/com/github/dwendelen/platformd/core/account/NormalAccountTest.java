package com.github.dwendelen.platformd.core.account;

import com.github.dwendelen.platformd.core.account.command.CreateNormalAccount;
import com.github.dwendelen.platformd.core.account.event.NormalAccountCreated;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Test;

import static org.junit.Assert.*;


public class NormalAccountTest {
    public static final String NAME = "name";
    private AggregateTestFixture<NormalAccount> fixture =
            new AggregateTestFixture<>(NormalAccount.class);

    @Test
    public void creation() {
        CreateNormalAccount createNormalAccount = new CreateNormalAccount();
        createNormalAccount.name = NAME;

        NormalAccountCreated normalAccountCreated = new NormalAccountCreated();
        normalAccountCreated.name = NAME;
        normalAccountCreated.uuid = createNormalAccount.uuid;

        fixture.when(createNormalAccount)
                .expectEvents(normalAccountCreated);
    }
}