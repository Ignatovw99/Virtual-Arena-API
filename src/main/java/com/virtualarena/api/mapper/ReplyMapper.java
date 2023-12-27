package com.virtualarena.api.mapper;

import com.virtualarena.api.controller.api.ReplyApi;
import com.virtualarena.api.domain.Question;
import com.virtualarena.api.domain.Reply;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.ReplyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReplyMapper extends DomainMapper<Reply, ReplyEntity, ReplyApi> {

    @Override
    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "eventId", source = "event.id")
    @Mapping(target = "questionId", source = "question.id")
    ReplyApi toApi(Reply domain);

    default Reply initializeReply(String content, Question question, User sender) {
        Reply reply = new Reply();

        reply.setQuestion(question);
        reply.setContent(content);
        reply.setEvent(question.getEvent());
        reply.setSender(sender);
        reply.setTimestamp(LocalDateTime.now());

        return reply;
    }
}
