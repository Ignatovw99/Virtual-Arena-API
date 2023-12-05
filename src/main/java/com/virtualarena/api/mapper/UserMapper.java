package com.virtualarena.api.mapper;

import com.virtualarena.api.controller.api.IdTokenClaims;
import com.virtualarena.api.controller.api.UserApi;
import com.virtualarena.api.controller.api.UserUpdateApi;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.UserEntity;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends DomainMapper<User, UserEntity, UserApi> {

    @Mapping(target = "profilePicture", source = "picture")
    @Mapping(target = "fullName", source = "name")
    User toUser(IdTokenClaims idTokenClaims);

    @Override
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "createdAtValue")
    UserEntity toEntity(User domain);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "profilePicture", ignore = true)
    void update(User userSource, @MappingTarget User userDestination);

    User toUser(UserUpdateApi userUpdateApi);

    @Named("createdAtValue")
    default LocalDate createdAtValue(LocalDate createdAt) {
        return Objects.isNull(createdAt) ?
                LocalDate.now() :
                createdAt;
    }
}
