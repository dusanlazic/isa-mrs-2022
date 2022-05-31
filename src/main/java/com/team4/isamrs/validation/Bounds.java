package com.team4.isamrs.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

@Constraint(validatedBy = BoundsValidator.class)
@Target({TYPE,ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bounds {

    String message() default "Bounds are not correctly set. Check for gaps and overlaps of required point ranges.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
