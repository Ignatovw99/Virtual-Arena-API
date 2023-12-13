package com.virtualarena.api.service.implementation;

import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.EventEntity;
import com.virtualarena.api.entity.UserEntity;
import com.virtualarena.api.exception.InvalidResourceStateException;
import com.virtualarena.api.exception.ResourceNotFoundException;
import com.virtualarena.api.mapper.UserMapper;
import com.virtualarena.api.repository.EventRepository;
import com.virtualarena.api.repository.UserRepository;
import com.virtualarena.api.service.contract.EventParticipantService;
import com.virtualarena.api.util.constant.EventConstants;
import com.virtualarena.api.util.constant.UserConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.virtualarena.api.util.constant.EventConstants.EVENT_RESOURCE;
import static com.virtualarena.api.util.constant.QuestionAndAnswerConstants.USER_IS_PARTICIPATING_IN_EVENT;
import static com.virtualarena.api.util.constant.UserConstants.USER_RESOURCE;

@Service
@RequiredArgsConstructor
public class EventParticipantServiceImpl implements EventParticipantService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> getAllEventParticipants(Long eventId) {
        EventEntity eventEntity = getEventEntityById(eventId);

        return eventEntity.getParticipants()
                .stream()
                .map(userMapper::toDomainFromEntity)
                .toList();
    }

    @Override
    public Boolean isUserParticipatingInEvent(Long userId, Long eventId) {
        return userRepository.findByIdAndEventsIdEquals(userId, eventId)
                .isPresent();
    }

    @Override
    public User participateInEvent(Long eventId, String userEmail) {
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(USER_RESOURCE, UserConstants.EMAIL, userEmail));
        EventEntity eventEntity = getEventEntityById(eventId);

        if (isUserParticipatingInEvent(userEntity.getId(), eventId)) {
            throw new InvalidResourceStateException(USER_IS_PARTICIPATING_IN_EVENT);
        }

        eventEntity.getParticipants()
                .add(userEntity);

        eventRepository.save(eventEntity);
        return userMapper.toDomainFromEntity(userEntity);
    }

    private EventEntity getEventEntityById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException(EVENT_RESOURCE, EventConstants.ID, eventId));
    }
}
