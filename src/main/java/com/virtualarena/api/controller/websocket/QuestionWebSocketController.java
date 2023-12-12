package com.virtualarena.api.controller.websocket;

import com.virtualarena.api.controller.api.QuestionApi;
import com.virtualarena.api.domain.Question;
import com.virtualarena.api.mapper.QuestionMapper;
import com.virtualarena.api.service.contract.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/events/{eventId}/questions")
@RequiredArgsConstructor
public class QuestionWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @MessageMapping
    public void postQuestion(@DestinationVariable Long eventId,
                                    @Payload String content) {
        Question question = questionService.createQuestion(eventId, content);
        QuestionApi questionResult = questionMapper.toApi(question);

        String destination = String.format("/topic/events/%d/questions", eventId);
        messagingTemplate.convertAndSend(destination, questionResult);
    }
}
