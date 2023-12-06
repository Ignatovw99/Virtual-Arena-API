package com.virtualarena.api.controller.api;

import com.virtualarena.api.domain.enums.EventCategory;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventApi {

    private Long id;

    private String title;

    private String description;

    private String imageUrl;

    private LocalDateTime startDateTime;

    private EventCategory category;

    private Long organizerId;
}
