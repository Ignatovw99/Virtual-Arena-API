package com.virtualarena.api.repository;

import com.virtualarena.api.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    List<QuestionEntity> findAllByEventIdEqualsOrderByTimestampDesc(Long eventId);

}
