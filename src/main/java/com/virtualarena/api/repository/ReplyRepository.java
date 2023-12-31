package com.virtualarena.api.repository;

import com.virtualarena.api.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    List<ReplyEntity> findAllByEventIdEqualsAndQuestionIdEqualsOrderByTimestampDesc(Long eventId, Long questionId);
}
