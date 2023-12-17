package com.virtualarena.api.service.implementation;

import com.virtualarena.api.domain.Like;
import com.virtualarena.api.domain.Question;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.exception.InvalidResourceStateException;
import com.virtualarena.api.service.contract.LikeService;
import com.virtualarena.api.service.contract.QuestionLikeService;
import com.virtualarena.api.service.contract.QuestionService;
import com.virtualarena.api.service.contract.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.virtualarena.api.util.constant.QuestionAndAnswerConstants.QUESTION_NOT_PART_OF_EVENT;

@Service
@RequiredArgsConstructor
public class QuestionLikeServiceImpl implements QuestionLikeService {

    private final LikeService likeService;
    private final QuestionService questionService;
    private final UserService userService;

    @Override
    public List<Like> getQuestionLikes(Long questionId, Long eventId) {
        Question question = questionService.getQuestionById(questionId);
        return likeService.getLikesByEventPostId(question.getId());
    }

    @Transactional
    @Override
    public Like likeQuestion(Long questionId, Long eventId) {
        User user = userService.getAuthenticationUser();
        Question question = questionService.getQuestionById(questionId);
        if (Objects.isNull(question.getEvent()) || !question.getEvent().getId().equals(eventId)) {
            throw new InvalidResourceStateException(QUESTION_NOT_PART_OF_EVENT);
        }
        return likeService.createLike(question, user);
    }
}
