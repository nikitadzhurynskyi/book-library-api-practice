package com.kiyotaka.booklibraryapipractice.domain.user.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class UserResponse {
    private UUID id;

    private String email;

    private Set<String> roles;
}
