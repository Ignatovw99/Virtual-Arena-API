package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.EventPost;
import com.virtualarena.api.domain.Like;
import com.virtualarena.api.domain.User;

import java.util.List;

public interface LikeService {

    List<Like> getLikesByEventPostId(Long eventPostId);

    Like createLike(EventPost eventPost, User user);
}
