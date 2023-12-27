package com.virtualarena.api.service.implementation;

import com.virtualarena.api.domain.Event;
import com.virtualarena.api.domain.Question;
import com.virtualarena.api.domain.Reply;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.ReplyEntity;
import com.virtualarena.api.exception.InvalidResourceStateException;
import com.virtualarena.api.mapper.ReplyMapper;
import com.virtualarena.api.repository.ReplyRepository;
import com.virtualarena.api.service.contract.EventParticipantService;
import com.virtualarena.api.service.contract.QuestionService;
import com.virtualarena.api.service.contract.ReplyService;
import com.virtualarena.api.service.contract.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.virtualarena.api.util.constant.QuestionAndAnswerConstants.REPLY_CONTENT_EMPTY;
import static com.virtualarena.api.util.constant.QuestionAndAnswerConstants.USER_IS_NOT_PARTICIPATING_IN_EVENT;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ReplyMapper replyMapper;
    private final QuestionService questionService;
    private final UserService userService;
    private final EventParticipantService eventParticipantService;

    @Override
    public List<Reply> getQuestionReplies(Long eventId, Long questionId) {
        List<ReplyEntity> replyEntities =
                replyRepository.findAllByEventIdEqualsAndQuestionIdEqualsOrderByTimestampDesc(eventId, questionId);
        return replyMapper.toDomainFromEntity(replyEntities);
    }

    @Override
    public Reply createReply(Long eventId, Long questionId, String content) {
        if (!isReplyContentValid(content)) {
            throw new InvalidResourceStateException(REPLY_CONTENT_EMPTY);
        }

        User sender = userService.getAuthenticationUser();
        Question question = questionService.getQuestionById(questionId);

        if (!canReplyToQuestion(sender, question)) {
            throw new InvalidResourceStateException(USER_IS_NOT_PARTICIPATING_IN_EVENT);
        }

        Reply reply = replyMapper.initializeReply(content, question, sender);

        ReplyEntity replyEntity = replyMapper.toEntity(reply);
        ReplyEntity savedReply = replyRepository.save(replyEntity);
        return replyMapper.toDomainFromEntity(savedReply);
    }

    private boolean isReplyContentValid(String content) {
        return Objects.nonNull(content) && !content.isBlank();
    }

    private boolean canReplyToQuestion(User user, Question question) {
        Event event = question.getEvent();
        return eventParticipantService.isUserParticipatingInEvent(user.getId(), event.getId()) ||
                user.getId().equals(event.getOrganizerId());
    }
}
