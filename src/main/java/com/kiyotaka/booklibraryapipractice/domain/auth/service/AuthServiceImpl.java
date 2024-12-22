package com.kiyotaka.booklibraryapipractice.domain.auth.service;

import com.kiyotaka.booklibraryapipractice.domain.auth.web.model.AuthRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.mapper.UserMapper;
import com.kiyotaka.booklibraryapipractice.domain.user.service.UserService;
import com.kiyotaka.booklibraryapipractice.domain.user.web.model.UserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserService userService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse register(AuthRequest authRequest) {
        if (userService.isExists(authRequest.getEmail())) {
            throw new RuntimeException("User already registered.");
        }
        authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        final UserEntity userEntity = userService.create(userMapper.mapAuthRequestToUserDto(authRequest));
        return userMapper.mapUserEntityToUserResponse(userEntity);
    }

    @Override
    public UserResponse login(AuthRequest authRequest) {
        return null;
    }
}
