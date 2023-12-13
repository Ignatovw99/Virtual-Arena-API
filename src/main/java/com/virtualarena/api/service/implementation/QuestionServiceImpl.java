package com.virtualarena.api.service.implementation;

import com.virtualarena.api.domain.Event;
import com.virtualarena.api.domain.Question;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.QuestionEntity;
import com.virtualarena.api.exception.InvalidResourceStateException;
import com.virtualarena.api.exception.ResourceNotFoundException;
import com.virtualarena.api.mapper.QuestionMapper;
import com.virtualarena.api.repository.QuestionRepository;
import com.virtualarena.api.service.contract.EventParticipantService;
import com.virtualarena.api.service.contract.EventService;
import com.virtualarena.api.service.contract.QuestionService;
import com.virtualarena.api.service.contract.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.virtualarena.api.util.constant.QuestionAndAnswerConstants.*;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final EventService eventService;
    private final UserService userService;
    private final EventParticipantService eventParticipantService;

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .map(questionMapper::toDomainFromEntity)
                .orElseThrow(() -> new ResourceNotFoundException(QUESTION_RESOURCE, ID, id));
    }

    @Override
    public List<Question> getEventQuestions(Long eventId) {
        List<QuestionEntity> questionEntities = questionRepository.findAllByEventIdEqualsOrderByTimestampDesc(eventId);
        return questionMapper.toDomainFromEntity(questionEntities);
    }

    @Override
    public Question createQuestion(Long eventId, String content) {
        if (!isQuestionContentValid(content)) {
            throw new InvalidResourceStateException(QUESTION_CONTENT_EMPTY);
        }

        Event event = eventService.getById(eventId);
        User sender = userService.getAuthenticationUser();
        if (!canCreateQuestion(sender, event)) {
            throw new InvalidResourceStateException(USER_IS_NOT_PARTICIPATING_IN_EVENT);
        }
        Question question = questionMapper.initializeQuestion(content, event, sender);

        QuestionEntity questionEntity = questionMapper.toEntity(question);
        QuestionEntity savedQuestion = questionRepository.save(questionEntity);
        return questionMapper.toDomainFromEntity(savedQuestion);
    }

    private boolean canCreateQuestion(User user, Event event) {
        return eventParticipantService.isUserParticipatingInEvent(user.getId(), event.getId()) ||
                user.getId().equals(event.getOrganizerId());
    }

    private boolean isQuestionContentValid(String content) {
        return Objects.nonNull(content) && !content.isBlank();
    }
}
