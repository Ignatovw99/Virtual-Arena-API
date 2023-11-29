package com.virtualarena.api.controller.api;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserApi {

    private Long id;

    private String email;

    private String fullName;

    private String phoneNumber;

    private String profilePicture;

    private String bio;

    private LocalDate createdAt;
}
