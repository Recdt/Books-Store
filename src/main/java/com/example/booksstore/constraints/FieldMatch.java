package com.example.booksstore.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {
    String first();
    String second();
    String message() default "Password and repeated password are not the same.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
