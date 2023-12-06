package com.virtualarena.api.mapper;

import com.virtualarena.api.controller.api.EventApi;
import com.virtualarena.api.controller.api.EventModifyApi;
import com.virtualarena.api.domain.Event;
import com.virtualarena.api.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper extends DomainMapper<Event, EventEntity, EventApi> {

    @Override
    @Mapping(target = "organizer.id", source = "organizerId")
    EventEntity toEntity(Event domain);

    @Override
    @Mapping(target = "organizerId", source = "organizer.id")
    Event toDomainFromEntity(EventEntity entity);

    Event toDomain(EventModifyApi eventModifyApi);
}
