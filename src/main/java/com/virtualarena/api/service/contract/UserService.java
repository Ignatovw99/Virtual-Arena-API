package com.virtualarena.api.service.contract;

import com.virtualarena.api.domain.User;

public interface UserService {

    User getByEmail(String email);

    User createUser(User user);
}
