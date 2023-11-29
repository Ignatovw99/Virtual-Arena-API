package com.virtualarena.api.validation.base;

import lombok.Value;

import java.util.List;
import java.util.Objects;

@Value
public class ValidationResult {

    boolean valid;

    List<String> messages;

    public static ValidationResult valid() {
        return new ValidationResult(true, List.of());
    }

    public static ValidationResult invalid(String message) {
        return new ValidationResult(false, List.of(message));
    }

    public static ValidationResult invalid(List<String> messages) {
        List<String> validationMessages = Objects.nonNull(messages) ?
                messages.stream().toList() :
                List.of();
        return new ValidationResult(false, validationMessages);
    }

    public boolean notValid() {
        return !isValid();
    }

    public String getMessage() {
        var hasMessages = Objects.isNull(messages) || messages.isEmpty();
        return hasMessages ?
                null :
                messages.get(0);
    }
}
