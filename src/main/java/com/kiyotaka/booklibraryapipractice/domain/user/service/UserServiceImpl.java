package com.kiyotaka.booklibraryapipractice.domain.user.service;

import com.kiyotaka.booklibraryapipractice.domain.user.dto.UserDto;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserEntity create(UserDto dto) {
        return null;
    }

    @Override
    public Optional<UserEntity> findBy(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findBy(UUID id) {
        return Optional.empty();
    }

    @Override
    public UserEntity findByOrThrow(String email) {
        return null;
    }

    @Override
    public UserEntity findByOrThrow(UUID id) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
