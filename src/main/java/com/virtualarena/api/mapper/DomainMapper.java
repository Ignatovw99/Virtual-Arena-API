package com.virtualarena.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface DomainMapper<D, E, A> {

    D toDomainFromEntity(E entity);

    E toEntity(D domain);

    A toApi(D domain);

    D toDomainFromApi(A api);

    void update(D source, @MappingTarget D destination);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePartially(D source, @MappingTarget D destination);
}
