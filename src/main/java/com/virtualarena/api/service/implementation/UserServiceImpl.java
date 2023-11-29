package com.virtualarena.api.service.implementation;

import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.UserEntity;
import com.virtualarena.api.exception.InvalidResourceStateException;
import com.virtualarena.api.exception.ResourceNotFoundException;
import com.virtualarena.api.mapper.UserMapper;
import com.virtualarena.api.repository.UserRepository;
import com.virtualarena.api.service.contract.UserService;
import com.virtualarena.api.validation.BeanValidationConstraintRule;
import com.virtualarena.api.validation.UniqueUserValidationRule;
import com.virtualarena.api.validation.base.ValidationResult;
import com.virtualarena.api.validation.base.ValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.virtualarena.api.util.constant.UserConstants.EMAIL;
import static com.virtualarena.api.util.constant.UserConstants.USER_RESOURCE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User createUser(User user) {
        ValidationRule<User> validator = new BeanValidationConstraintRule<User>()
                .chainWith(new UniqueUserValidationRule(this));
        ValidationResult validationResult = validator.validate(user);

        if (validationResult.notValid()) {
            throw new InvalidResourceStateException(validationResult.getMessage());
        }

        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity createdUser = userRepository.save(userEntity);
        return userMapper.toDomainFromEntity(createdUser);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDomainFromEntity)
                .orElseThrow(() -> new ResourceNotFoundException(USER_RESOURCE, EMAIL, email));
    }
}
