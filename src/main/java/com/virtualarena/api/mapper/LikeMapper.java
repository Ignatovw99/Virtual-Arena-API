package com.virtualarena.api.mapper;

import com.virtualarena.api.controller.api.QuestionLikeApi;
import com.virtualarena.api.domain.Like;
import com.virtualarena.api.entity.LikeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = EventMapper.class
)
public interface LikeMapper {

    LikeEntity toEntity(Like like);

    Like toDomain(LikeEntity like);

    List<Like> toDomain(List<LikeEntity> likes);

    @Mapping(target = "questionId", source = "eventPost.id")
    @Mapping(target = "userId", source = "user.id")
    QuestionLikeApi toQuestionLike(Like like);

    List<QuestionLikeApi> toQuestionLike(List<Like> likes);
}
