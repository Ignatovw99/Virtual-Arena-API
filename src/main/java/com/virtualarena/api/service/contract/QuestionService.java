package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.Question;

import java.util.List;

public interface QuestionService {

    Question getQuestionById(Long id);

    List<Question> getEventQuestions(Long eventId);

    Question createQuestion(Long eventId, String content);
}
