package com.virtualarena.api.repository;

import com.virtualarena.api.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    List<LikeEntity> findAllByEventPostIdEquals(Long eventPostId);
}
