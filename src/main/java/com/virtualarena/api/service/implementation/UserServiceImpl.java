package com.virtualarena.api.service.implementation;

import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.UserEntity;
import com.virtualarena.api.exception.InvalidResourceStateException;
import com.virtualarena.api.exception.ResourceNotFoundException;
import com.virtualarena.api.mapper.UserMapper;
import com.virtualarena.api.repository.UserRepository;
import com.virtualarena.api.service.contract.FileService;
import com.virtualarena.api.service.contract.UserService;
import com.virtualarena.api.validation.BeanValidationConstraintRule;
import com.virtualarena.api.validation.UniqueUserValidationRule;
import com.virtualarena.api.validation.UpdateUserValidationRule;
import com.virtualarena.api.validation.base.ValidationResult;
import com.virtualarena.api.validation.base.ValidationRule;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static com.virtualarena.api.util.constant.UserConstants.EMAIL;
import static com.virtualarena.api.util.constant.UserConstants.USER_RESOURCE;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileService fileService;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDomainFromEntity)
                .orElseThrow(() -> new ResourceNotFoundException(USER_RESOURCE, EMAIL, email));
    }

    @Override
    public User saveUser(User user) {
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
    public User updateUser(User user, MultipartFile pictureFile, Authentication authentication) {
        User userToUpdate = getByEmail(user.getEmail());
        userMapper.update(user, userToUpdate);

        ValidationResult validationResult = new UpdateUserValidationRule(authentication)
                .chainWith(new UniqueUserValidationRule(this))
                .validate(userToUpdate);
        if (validationResult.notValid()) {
            throw new InvalidResourceStateException(validationResult.getMessage());
        }

        if (Objects.nonNull(pictureFile)) {
            String pictureUrl = fileService.uploadFile(pictureFile);
            userToUpdate.setProfilePicture(pictureUrl);
        }
        return saveUser(userToUpdate);
    }
}
