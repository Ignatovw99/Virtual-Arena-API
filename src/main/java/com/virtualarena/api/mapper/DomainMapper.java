package com.virtualarena.api.mapper;

public interface DomainMapper<D, E, A> {

    D toDomainFromEntity(E entity);

    E toEntity(D domain);

    A toApi(D domain);

    D toDomainFromApi(A api);
}
