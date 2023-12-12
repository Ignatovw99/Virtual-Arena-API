package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.User;

import java.util.List;

public interface EventParticipantService {

    List<User> getAllEventParticipants(Long eventId);

    User participateInEvent(Long eventId, String userEmail);
}
