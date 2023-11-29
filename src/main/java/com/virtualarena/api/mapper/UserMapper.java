package com.virtualarena.api.mapper;

import com.virtualarena.api.controller.api.IdTokenClaims;
import com.virtualarena.api.controller.api.UserApi;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends DomainMapper<User, UserEntity, UserApi> {

    User toUser(IdTokenClaims idTokenClaims);

    @Override
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "createdAtValue")
    UserEntity toEntity(User domain);

    @Named("createdAtValue")
    default LocalDate createdAtValue(LocalDate createdAt) {
        return Objects.isNull(createdAt) ?
                LocalDate.now() :
                createdAt;
    }
}
