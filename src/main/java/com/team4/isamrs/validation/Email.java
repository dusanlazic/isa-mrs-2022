package com.team4.isamrs.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = EmailValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Email {

    String message() default "Email must be valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}