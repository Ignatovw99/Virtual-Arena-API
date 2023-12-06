package com.virtualarena.api.service.implementation;

import com.virtualarena.api.domain.Event;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.EventEntity;
import com.virtualarena.api.exception.InvalidResourceStateException;
import com.virtualarena.api.mapper.EventMapper;
import com.virtualarena.api.repository.EventRepository;
import com.virtualarena.api.service.contract.EventService;
import com.virtualarena.api.service.contract.FileService;
import com.virtualarena.api.service.contract.UserService;
import com.virtualarena.api.validation.BeanValidationConstraintRule;
import com.virtualarena.api.validation.base.ValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;
    private final FileService fileService;

    @Override
    public Event createEvent(Event event, String organizerEmail) {
        ValidationResult validationResult = new BeanValidationConstraintRule<>()
                .validate(event);
        if (validationResult.notValid()) {
            throw new InvalidResourceStateException(validationResult.getMessage());
        }

        User organizer = userService.getByEmail(organizerEmail);
        event.setOrganizerId(organizer.getId());

        if (Objects.nonNull(event.getImageFile())) {
            String imageUrl = fileService.uploadFile(event.getImageFile());
            event.setImageUrl(imageUrl);
        }

        EventEntity eventEntity = eventMapper.toEntity(event);
        EventEntity savedEvent = eventRepository.save(eventEntity);
        return eventMapper.toDomainFromEntity(savedEvent);
    }
}
