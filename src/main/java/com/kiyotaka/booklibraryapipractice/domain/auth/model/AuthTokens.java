package com.kiyotaka.booklibraryapipractice.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthTokens {
    private final String accessToken;

    private final String refreshToken;
}
