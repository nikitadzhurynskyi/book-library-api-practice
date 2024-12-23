package com.kiyotaka.booklibraryapipractice.domain.user.mapper;

import com.kiyotaka.booklibraryapipractice.domain.auth.web.model.AuthRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.dto.UserDto;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.RoleEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.web.model.UserResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto mapAuthRequestToUserDto(AuthRequest authRequest) {
        return new UserDto(authRequest.getEmail(), authRequest.getPassword());
    }

    public UserResponse mapUserEntityToUserResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet())
        );
    }
}
