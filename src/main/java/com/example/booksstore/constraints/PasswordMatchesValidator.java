package com.example.booksstore.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Objects;

public class PasswordMatchesValidator implements
        ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value,
                           ConstraintValidatorContext constraintValidatorContext) {
        Object firstFieldValue = getFieldValue(value, firstFieldName);
        Object secondFieldValue = getFieldValue(value, secondFieldName);
        return Objects.equals(firstFieldValue, secondFieldValue);
    }

    private Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Could not access field " + fieldName, e);
        }
    }
}
