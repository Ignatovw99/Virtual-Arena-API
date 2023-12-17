package com.virtualarena.api.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventPost {

    private Long id;

    private String content;

    private LocalDateTime timestamp;

    private User sender;

    private Event event;
}
