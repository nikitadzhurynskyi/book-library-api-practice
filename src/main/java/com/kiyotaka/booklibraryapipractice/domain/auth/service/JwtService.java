package com.kiyotaka.booklibraryapipractice.domain.auth.service;

import com.kiyotaka.booklibraryapipractice.domain.auth.model.AuthTokens;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenClaims;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenType;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import io.jsonwebtoken.Claims;

import java.util.UUID;
import java.util.function.Function;

public interface JwtService {
    AuthTokens generateTokens(TokenClaims claims);

    boolean validateAccessToken(String token, UserEntity userEntity);

    boolean validateRefreshToken(String token);

    Claims parseAllClaims(String token, TokenType type);

    <T> T parseClaim(String token, TokenType type, Function<Claims, T> handler);

    String parseUsername(String token, TokenType type);

    UUID parseUserId(String token, TokenType type);

    boolean isExpired(String token, TokenType type);
}
