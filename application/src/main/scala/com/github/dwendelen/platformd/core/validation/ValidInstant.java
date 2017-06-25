package com.github.dwendelen.platformd.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@NotNull
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidInstant {
    String message() default "Invalid name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
