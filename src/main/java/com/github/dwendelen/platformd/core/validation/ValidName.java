package com.github.dwendelen.platformd.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NotNull
@Size(min = 1)
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidName {
    String message() default "Amount must be greater then 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
