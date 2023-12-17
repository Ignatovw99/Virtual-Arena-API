package com.virtualarena.api.service.implementation;

import com.virtualarena.api.domain.EventPost;
import com.virtualarena.api.domain.Like;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.entity.LikeEntity;
import com.virtualarena.api.mapper.LikeMapper;
import com.virtualarena.api.repository.LikeRepository;
import com.virtualarena.api.service.contract.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;

    @Override
    public List<Like> getLikesByEventPostId(Long eventPostId) {
        List<LikeEntity> likes = likeRepository.findAllByEventPostIdEquals(eventPostId);
        return likeMapper.toDomain(likes);
    }

    @Override
    public Like createLike(EventPost eventPost, User user) {
        Like like = new Like();
        like.setUser(user);
        like.setEventPost(eventPost);

        LikeEntity likeEntity = likeMapper.toEntity(like);
        LikeEntity savedLike = likeRepository.save(likeEntity);
        return likeMapper.toDomain(savedLike);
    }
}
