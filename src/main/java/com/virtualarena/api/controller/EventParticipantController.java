package com.virtualarena.api.controller;

import com.virtualarena.api.controller.api.UserApi;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.mapper.UserMapper;
import com.virtualarena.api.service.contract.EventParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/participants")
@RequiredArgsConstructor
public class EventParticipantController {

    private final EventParticipantService eventParticipantService;
    private final UserMapper userMapper;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ResponseEntity<List<UserApi>> getEventParticipants(@PathVariable Long eventId) {
        List<User> participants = eventParticipantService.getAllEventParticipants(eventId);
        List<UserApi> participantApis = userMapper.toApi(participants);

        return ResponseEntity.ok(participantApis);
    }

    @PostMapping
    public ResponseEntity<UserApi> participateInEvent(@PathVariable Long eventId,
                                                      Authentication authentication) {
        User participant = eventParticipantService.participateInEvent(eventId, authentication.getName());
        UserApi participantApi = userMapper.toApi(participant);

        String destination = String.format("/topic/events/%d/participants", eventId);
        messagingTemplate.convertAndSend(destination, participantApi);
        return ResponseEntity.ok(participantApi);
    }
}
