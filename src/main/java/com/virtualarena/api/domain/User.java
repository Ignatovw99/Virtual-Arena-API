package com.virtualarena.api.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

import static com.virtualarena.api.util.constant.UserConstants.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = EMAIL_SHOULD_BE_PRESENT)
    @EqualsAndHashCode.Include
    private String email;

    @NotBlank(message = FULL_NAME_SHOULD_BE_PRESENT)
    private String fullName;

    private String phoneNumber;

    @NotBlank(message = PROFILE_PICTURE_SHOULD_BE_PRESENT)
    private String profilePicture;

    private String bio;

    private LocalDate createdAt;
}
