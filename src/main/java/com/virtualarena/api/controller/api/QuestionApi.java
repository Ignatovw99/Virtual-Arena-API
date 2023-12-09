package com.virtualarena.api.controller.api;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionApi {


    private Long id;

    private String content;

    private LocalDateTime timestamp;

    private Long senderId;

    private Long eventId;
}
