package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User getAuthenticationUser();

    User getByEmail(String email);

    User getById(Long id);

    User saveUser(User user);

    User updateUser(User user, MultipartFile pictureFile, Authentication authentication);
}
