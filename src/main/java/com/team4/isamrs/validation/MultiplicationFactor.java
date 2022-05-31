package com.team4.isamrs.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Constraint(validatedBy = MultiplicationFactorValidator.class)
@Target({TYPE,ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiplicationFactor {

    String message() default "Multiplication factor must be in (0.0, 1.0] for customers, or in [1.0, inf) for advertisers.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
