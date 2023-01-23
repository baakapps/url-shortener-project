package com.baakapp.urlshortenerservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UrlValidation.class)
@Documented
public @interface ValidUrl {

    String message() default "It is not valid URL format";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}