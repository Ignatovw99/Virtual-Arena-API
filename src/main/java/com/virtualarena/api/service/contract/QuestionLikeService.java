package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.Like;

import java.util.List;

public interface QuestionLikeService {

    List<Like> getQuestionLikes(Long questionId, Long eventId);

    Like likeQuestion(Long questionId, Long eventId);
}
