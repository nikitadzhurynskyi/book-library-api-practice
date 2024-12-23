package com.kiyotaka.booklibraryapipractice.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class TokenClaims {
    private String username;

    private UUID userId;
}
