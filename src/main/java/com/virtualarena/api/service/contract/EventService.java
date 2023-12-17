package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.Event;

import java.util.List;

public interface EventService {

    List<Event> getAll();

    List<Event> getAllUserEvents(Long userId);

    Event getById(Long id);

    Event createEvent(Event event);
}
