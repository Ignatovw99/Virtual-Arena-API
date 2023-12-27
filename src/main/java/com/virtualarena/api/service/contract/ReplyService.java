package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.Reply;

import java.util.List;

public interface ReplyService {

    List<Reply> getQuestionReplies(Long eventId, Long questionId);

    Reply createReply(Long eventId, Long questionId, String content);
}
