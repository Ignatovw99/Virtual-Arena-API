package com.virtualarena.api.controller;

import com.virtualarena.api.controller.api.IdTokenClaims;
import com.virtualarena.api.controller.api.UserApi;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.mapper.UserMapper;
import com.virtualarena.api.service.contract.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/profile")
    public ResponseEntity<UserApi> getUserProfile(Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userService.getByEmail(userEmail);
        UserApi result = userMapper.toApi(user);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserApi> createUserProfile(@RequestBody IdTokenClaims idTokenClaims) {
        User user = userMapper.toUser(idTokenClaims);
        User createdUser = userService.createUser(user);
        UserApi result = userMapper.toApi(createdUser);

        return ResponseEntity.created(URI.create("/api/users/profile"))
                .body(result);
    }
}