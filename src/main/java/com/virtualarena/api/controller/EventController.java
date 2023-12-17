package com.virtualarena.api.controller;

import com.virtualarena.api.controller.api.EventApi;
import com.virtualarena.api.controller.api.EventSaveApi;
import com.virtualarena.api.domain.Event;
import com.virtualarena.api.mapper.EventMapper;
import com.virtualarena.api.service.contract.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<List<EventApi>> getAllEvents(@RequestParam(required = false) Long userId) {
        List<Event> events = Objects.isNull(userId) ?
                eventService.getAll() :
                eventService.getAllUserEvents(userId);
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
    public ResponseEntity<EventApi> createEvent(@ModelAttribute EventSaveApi eventApi) {
        Event event = eventMapper.toDomain(eventApi);
        Event createdEvent = eventService.createEvent(event);
        EventApi eventResult = eventMapper.toApi(createdEvent);

        return ResponseEntity.created(URI.create("/api/events/" + eventResult.getId()))
                .body(eventResult);
    }
}
