package com.virtualarena.api.validation;

import com.virtualarena.api.domain.User;
import com.virtualarena.api.validation.base.ValidationResult;
import com.virtualarena.api.validation.base.ValidationRule;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;

import java.util.Objects;

import static com.virtualarena.api.util.constant.UserConstants.*;

@RequiredArgsConstructor
public class UpdateUserValidationRule extends ValidationRule<User> {

    private final Authentication authentication;

    @Override
    protected ValidationResult validateRule(User userToValidate) {
        if (Objects.isNull(authentication) || Objects.isNull(userToValidate)) {
            return ValidationResult.invalid(USER_IS_MISSING);
        }
        if (!userToValidate.getEmail().equals(authentication.getName())) {
            return ValidationResult.invalid(EMAIL_CANNOT_BE_CHANGED);
        }
        if (StringUtils.isBlank(userToValidate.getFullName())) {
            return ValidationResult.invalid(FULL_NAME_SHOULD_BE_PRESENT);
        }

        return ValidationResult.valid();
    }
}
