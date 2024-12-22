package com.kiyotaka.booklibraryapipractice.domain.user.service;

import com.kiyotaka.booklibraryapipractice.domain.user.dto.UserDto;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    UserEntity create(UserDto dto);

    Optional<UserEntity> findBy(String email);

    Optional<UserEntity> findBy(UUID id);

    UserEntity findByOrThrow(String email);

    UserEntity findByOrThrow(UUID id);

}
