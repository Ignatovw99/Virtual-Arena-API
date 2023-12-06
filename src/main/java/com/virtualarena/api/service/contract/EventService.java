package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.Event;

public interface EventService {

    Event createEvent(Event event, String organizerEmail);
}
