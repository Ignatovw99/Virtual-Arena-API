package com.virtualarena.api.controller;

import com.virtualarena.api.controller.api.QuestionApi;
import com.virtualarena.api.controller.api.QuestionCreateApi;
import com.virtualarena.api.domain.Question;
import com.virtualarena.api.mapper.QuestionMapper;
import com.virtualarena.api.service.contract.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping
    public ResponseEntity<List<QuestionApi>> getEventQuestions(@PathVariable Long eventId) {
        List<Question> questions = questionService.getEventQuestions(eventId);
        List<QuestionApi> questionApis = questionMapper.toApi(questions);

        return ResponseEntity.ok(questionApis);
    }

    @PostMapping
    public ResponseEntity<QuestionApi> createEventQuestion(@PathVariable Long eventId,
                                                           @RequestBody QuestionCreateApi questionCreateApi) {
        Question question = questionService.createQuestion(eventId, questionCreateApi.getContent());
        QuestionApi questionApi = questionMapper.toApi(question);
        return ResponseEntity.created(URI.create(String.format("/api/events/%d/questions", eventId)))
                .body(questionApi);
    }
}
