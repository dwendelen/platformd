package com.github.dwendelen.platformd.core.account.command;

import com.github.dwendelen.platformd.core.validation.ValidAmount;
import com.github.dwendelen.platformd.core.validation.ValidName;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

public class CreateNormalAccount {
    @NotNull
    public UUID uuid = UUID.randomUUID();
    @ValidName
    public String name;
    @ValidAmount
    public BigDecimal initialBalance = BigDecimal.ZERO;
}
