package com.virtualarena.api.controller;

import com.virtualarena.api.controller.api.IdTokenClaims;
import com.virtualarena.api.controller.api.UserApi;
import com.virtualarena.api.controller.api.UserUpdateApi;
import com.virtualarena.api.domain.User;
import com.virtualarena.api.mapper.UserMapper;
import com.virtualarena.api.service.contract.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/users/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserApi> getUserProfile() {
        User user = userService.getAuthenticationUser();
        UserApi result = userMapper.toApi(user);

        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserApi> getUserProfileById(@PathVariable Long id) {
        User user = userService.getById(id);
        UserApi userApi = userMapper.toApi(user);

        return ResponseEntity.ok(userApi);
    }

    @PostMapping
    public ResponseEntity<UserApi> createUserProfile(@RequestBody IdTokenClaims idTokenClaims) {
        User user = userMapper.toUser(idTokenClaims);
        User createdUser = userService.saveUser(user);
        UserApi result = userMapper.toApi(createdUser);

        return ResponseEntity.created(URI.create("/api/users/profile"))
                .body(result);
    }

    @PutMapping
    public ResponseEntity<UserApi> updateUserProfile(@ModelAttribute UserUpdateApi userUpdateApi,
                                                     @RequestPart(required = false) MultipartFile pictureFile,
                                                     Authentication authentication) {
        User userToUpdate = userMapper.toUser(userUpdateApi);
        User user = userService.updateUser(userToUpdate, pictureFile, authentication);
        return ResponseEntity.ok(userMapper.toApi(user));
    }
}
