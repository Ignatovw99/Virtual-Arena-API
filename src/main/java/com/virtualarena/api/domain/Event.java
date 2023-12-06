package com.virtualarena.api.domain;

import com.virtualarena.api.domain.enums.EventCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static com.virtualarena.api.util.constant.EventConstants.*;

@Data
public class Event {

    private Long id;

    @NotBlank(message = TITLE_SHOULD_BE_PRESENT)
    private String title;

    @NotBlank(message = DESCRIPTION_SHOULD_BE_PRESENT)
    private String description;

    private String imageUrl;

    private MultipartFile imageFile;

    @NotNull(message = START_DATE_TIME_SHOULD_BE_PRESENT)
    private LocalDateTime startDateTime;

    @NotNull(message = CATEGORY_SHOULD_BE_PRESENT)
    private EventCategory category;

    private Long organizerId;
}
