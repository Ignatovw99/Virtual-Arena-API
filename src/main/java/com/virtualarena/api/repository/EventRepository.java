package com.virtualarena.api.repository;

import com.virtualarena.api.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findAllByParticipantsIdEqualsOrOrganizerIdEquals(Long participantId, Long organizerId);
}
