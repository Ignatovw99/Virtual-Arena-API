package com.virtualarena.api.controller;

import com.virtualarena.api.controller.api.QuestionLikeApi;
import com.virtualarena.api.domain.Like;
import com.virtualarena.api.mapper.LikeMapper;
import com.virtualarena.api.service.contract.QuestionLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/questions/{questionId}")
@RequiredArgsConstructor
public class QuestionLikeController {

    private final QuestionLikeService questionLikeService;
    private final LikeMapper likeMapper;

    @GetMapping
    public ResponseEntity<List<QuestionLikeApi>> getQuestionLikes(@PathVariable Long eventId,
                                                                  @PathVariable Long questionId) {
        List<Like> like = questionLikeService.getQuestionLikes(questionId, eventId);
        List<QuestionLikeApi> likeApis = likeMapper.toQuestionLike(like);

        return ResponseEntity.ok(likeApis);
    }

    @PostMapping
    public ResponseEntity<QuestionLikeApi> likeQuestion(@PathVariable Long eventId,
                                                        @PathVariable Long questionId) {
        Like like = questionLikeService.likeQuestion(questionId, eventId);
        QuestionLikeApi likeApi = likeMapper.toQuestionLike(like);

        return ResponseEntity.created(URI.create(String.format("/api/events/%d/questions/%d", eventId, questionId)))
                .body(likeApi);
    }
}
