package com.virtualarena.api.controller.api;

import lombok.Data;

@Data
public class UserUpdateApi {

    private Long id;

    private String email;

    private String fullName;

    private String phoneNumber;

    private String bio;
}
