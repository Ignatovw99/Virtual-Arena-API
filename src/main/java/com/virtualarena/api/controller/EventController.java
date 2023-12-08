package com.virtualarena.api.controller;

import com.virtualarena.api.controller.api.EventApi;
import com.virtualarena.api.controller.api.EventModifyApi;
import com.virtualarena.api.domain.Event;
import com.virtualarena.api.mapper.EventMapper;
import com.virtualarena.api.service.contract.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<List<EventApi>> getAllEvents() {
        List<Event> events = eventService.getAll();
        List<EventApi> eventApis = eventMapper.toApi(events);

        return ResponseEntity.ok(eventApis);
    }

    @GetMapping("{id}")
    public ResponseEntity<EventApi> getEventById(@PathVariable Long id) {
        Event event = eventService.getById(id);
        EventApi eventApi = eventMapper.toApi(event);

        return ResponseEntity.ok(eventApi);
    }

    @PostMapping
    public ResponseEntity<EventApi> createEvent(@ModelAttribute EventModifyApi eventApi,
                                                Authentication authentication) {
        Event event = eventMapper.toDomain(eventApi);
        Event createdEvent = eventService.createEvent(event, authentication.getName());
        EventApi eventResult = eventMapper.toApi(createdEvent);

        return ResponseEntity.created(URI.create("/api/events/" + eventResult.getId()))
                .body(eventResult);
    }
}
