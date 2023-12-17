package com.virtualarena.api.controller.websocket;

import com.virtualarena.api.controller.api.QuestionLikeApi;
import com.virtualarena.api.domain.Like;
import com.virtualarena.api.mapper.LikeMapper;
import com.virtualarena.api.service.contract.QuestionLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/events/{eventId}/questions/{questionId}/likes")
@RequiredArgsConstructor
public class QuestionLikeWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final QuestionLikeService questionLikeService;
    private final LikeMapper likeMapper;

    @MessageMapping
    public void likeQuestion(@DestinationVariable Long eventId,
                             @DestinationVariable Long questionId) {
        Like like = questionLikeService.likeQuestion(questionId, eventId);
        QuestionLikeApi likeApi = likeMapper.toQuestionLike(like);

        String destination = String.format("/topic/events/%d/questions/%d/likes", eventId, questionId);
        messagingTemplate.convertAndSend(destination, likeApi);
    }
}
