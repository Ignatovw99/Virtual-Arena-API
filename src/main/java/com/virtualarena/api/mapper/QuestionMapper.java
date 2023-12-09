package com.virtualarena.api.mapper;

import com.virtualarena.api.controller.api.QuestionApi;
import com.virtualarena.api.domain.Event;
import com.virtualarena.api.domain.Question;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.QuestionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper extends DomainMapper<Question, QuestionEntity, QuestionApi> {

    @Override
    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "eventId", source = "event.id")
    QuestionApi toApi(Question domain);

    default Question initializeQuestion(String content, Event event, User sender) {
        Question question = new Question();

        question.setContent(content);
        question.setEvent(event);
        question.setSender(sender);
        question.setTimestamp(LocalDateTime.now());

        return question;
    }
}
