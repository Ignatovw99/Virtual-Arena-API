package com.virtualarena.api.validation.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public abstract class ValidationRule<T> {

    private ValidationRule<T> next;

    public ValidationRule<T> chainWith(ValidationRule<T> next) {
        if (isNull(this.next)) {
            this.next = next;
            return this;
        }
        addValidationRuleToChainEnd(next);
        return this;
    }

    public ValidationResult validate(T toValidate) {
        List<String> validationMessages = runValidationChain(toValidate);
        return validationMessages.isEmpty() ?
                ValidationResult.valid() :
                ValidationResult.invalid(validationMessages);
    }

    protected abstract ValidationResult validateRule(T toValidate);

    private void addValidationRuleToChainEnd(ValidationRule<T> next) {
        ValidationRule<T> nextRule = this.next;
        while (nonNull(nextRule.next)) {
            nextRule = nextRule.next;
        }
        nextRule.next = next;
    }

    private List<String> runValidationChain(T toValidate) {
        List<String> invalidMessages = new ArrayList<>();

        ValidationRule<T> currentValidationRule = this;
        while (Objects.nonNull(currentValidationRule)) {
            ValidationResult validationResult = currentValidationRule.validateRule(toValidate);
            if (validationResult.notValid()) {
                invalidMessages.addAll(validationResult.getMessages());
            }
            currentValidationRule = currentValidationRule.next;
        }

        return invalidMessages;
    }
}
