package com.team4.isamrs.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CountryCodeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CountryCode {

    String message() default "Field must be equal to ISO 3116 country code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
