package com.kiyotaka.booklibraryapipractice.domain.user.service;

import com.kiyotaka.booklibraryapipractice.domain.user.dto.UserDto;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.RoleEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity create(UserDto dto) {
        final UserEntity user = new UserEntity(dto.getEmail(), dto.getPassword());
        user.setRoles(Set.of(new RoleEntity("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findBy(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserEntity> findBy(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity findByOrThrow(String email) {
        return findBy(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserEntity findByOrThrow(UUID id) {
        return findBy(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public boolean isExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByOrThrow(username);
    }
}
