package com.virtualarena.api.controller.api;

import com.virtualarena.api.domain.enums.EventCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventModifyApi {

    private String title;

    private String description;

    private MultipartFile imageFile;

    private LocalDateTime startDateTime;

    private EventCategory category;
}
