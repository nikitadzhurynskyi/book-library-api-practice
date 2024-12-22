package com.kiyotaka.booklibraryapipractice.domain.auth.service;

import com.kiyotaka.booklibraryapipractice.domain.auth.model.AuthTokens;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenClaims;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenType;
import io.jsonwebtoken.Claims;

import java.util.UUID;
import java.util.function.Function;

public interface JwtService {
    AuthTokens generateTokens(TokenClaims claims);

    //TODO: add user class
    boolean validateToken(String token, TokenType type);

    Claims parseAllClaims(String token, TokenType type);

    <T> T parseClaim(String token, TokenType type, Function<Claims, T> handler);

    String parseUsername(String token, TokenType type);

    UUID parseUserId(String token, TokenType type);

    boolean isExpired(String token, TokenType type);
}
