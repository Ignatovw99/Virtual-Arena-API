package com.virtualarena.api.validation;

import com.virtualarena.api.validation.base.ValidationResult;
import com.virtualarena.api.validation.base.ValidationRule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

import static com.virtualarena.util.constant.Constants.VALIDATION_NOT_POSSIBLE_OBJECT_IS_NULL;
import static java.util.Objects.isNull;

public class BeanValidationConstraintRule<T> extends ValidationRule<T> {

    @Override
    protected ValidationResult validateRule(T toValidate) {
        if (isNull(toValidate)) {
            return ValidationResult.invalid(VALIDATION_NOT_POSSIBLE_OBJECT_IS_NULL);
        }

        try (var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<T>> constraintViolations = validator.validate(toValidate);
            List<String> messages = constraintViolations.stream()
                    .map(ConstraintViolation::getMessage)
                    .toList();
            if (!constraintViolations.isEmpty()) {
                return ValidationResult.invalid(messages);
            }
        }
        return ValidationResult.valid();
    }
}
