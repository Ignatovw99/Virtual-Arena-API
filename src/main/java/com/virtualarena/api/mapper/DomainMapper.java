package com.virtualarena.api.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

public interface DomainMapper<D, E, A> {

    D toDomainFromEntity(E entity);

    List<D> toDomainFromEntity(List<E> entities);

    E toEntity(D domain);

    A toApi(D domain);

    List<A> toApi(List<D> domainModels);

    D toDomainFromApi(A api);

    @Mapping(target = "id", ignore = true)
    void update(D source, @MappingTarget D destination);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updatePartially(D source, @MappingTarget D destination);
}
