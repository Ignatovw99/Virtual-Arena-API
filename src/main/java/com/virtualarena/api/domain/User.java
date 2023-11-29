package com.virtualarena.api.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

import static com.virtualarena.api.util.constant.UserConstants.*;

@Data
public class User {

    private Long id;

    @NotBlank(message = EMAIL_SHOULD_BE_PRESENT)
    private String email;

    @NotBlank(message = FULL_NAME_SHOULD_BE_PRESENT)
    private String fullName;

    private String phoneNumber;

    @NotBlank(message = PROFILE_PICTURE_SHOULD_BE_PRESENT)
    private String profilePicture;

    private String bio;

    private LocalDate createdAt;
}
