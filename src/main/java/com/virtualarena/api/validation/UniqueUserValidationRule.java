package com.virtualarena.api.validation;

import com.virtualarena.api.domain.User;
import com.virtualarena.api.exception.ResourceNotFoundException;
import com.virtualarena.api.service.contract.UserService;
import com.virtualarena.api.validation.base.ValidationResult;
import com.virtualarena.api.validation.base.ValidationRule;
import com.virtualarena.api.util.constant.UserConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueUserValidationRule extends ValidationRule<User> {

    private final UserService userService;

    @Override
    protected ValidationResult validateRule(User userToValidate) {
        String email = userToValidate.getEmail();
        try {
            userService.getByEmail(email);
        } catch (ResourceNotFoundException exception) {
            return ValidationResult.valid();
        }
        return ValidationResult.invalid(UserConstants.USER_ALREADY_EXISTS);
    }
}
