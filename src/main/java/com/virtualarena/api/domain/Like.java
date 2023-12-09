package com.virtualarena.api.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {

    private Long id;

    private User user;

    private EventPost eventPost;
}
