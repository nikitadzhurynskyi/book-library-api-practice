package com.kiyotaka.booklibraryapipractice.domain.user.repository;

import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}
