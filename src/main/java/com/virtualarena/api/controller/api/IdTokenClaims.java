package com.virtualarena.api.controller.api;

import lombok.Data;

@Data
public class IdTokenClaims {

    private String email;

    private String name;

    private String picture;
}
