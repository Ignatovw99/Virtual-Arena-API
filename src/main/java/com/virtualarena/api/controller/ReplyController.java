package com.virtualarena.api.controller;

import com.virtualarena.api.controller.api.ReplyApi;
import com.virtualarena.api.domain.Reply;
import com.virtualarena.api.mapper.ReplyMapper;
import com.virtualarena.api.service.contract.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/questions/{questionId}/replies")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final ReplyMapper replyMapper;

    @GetMapping
    public ResponseEntity<List<ReplyApi>> getQuestionReplies(@PathVariable Long eventId,
                                                             @PathVariable Long questionId) {
        List<Reply> replies = replyService.getQuestionReplies(eventId, questionId);
        List<ReplyApi> replyApis = replyMapper.toApi(replies);

        return ResponseEntity.ok(replyApis);
    }
}
