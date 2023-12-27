package com.virtualarena.api.controller.websocket;

import com.virtualarena.api.controller.api.ReplyApi;
import com.virtualarena.api.domain.Reply;
import com.virtualarena.api.mapper.ReplyMapper;
import com.virtualarena.api.service.contract.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/events/{eventId}/questions/{questionId}/replies")
@RequiredArgsConstructor
public class ReplyWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    @MessageMapping
    public void postReply(@DestinationVariable Long eventId,
                          @DestinationVariable Long questionId,
                          @Payload String content) {
        Reply reply = replyService.createReply(eventId, questionId, content);
        ReplyApi replyApi = replyMapper.toApi(reply);

        String destination = String.format("/topic/events/%d/questions/%d/replies", eventId, questionId);
        messagingTemplate.convertAndSend(destination, replyApi);
    }
}
