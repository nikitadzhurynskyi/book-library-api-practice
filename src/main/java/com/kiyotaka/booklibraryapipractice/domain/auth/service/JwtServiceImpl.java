package com.kiyotaka.booklibraryapipractice.domain.auth.service;

import com.kiyotaka.booklibraryapipractice.domain.auth.model.AuthTokens;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenClaims;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${book-library.jwt.access.secret}")
    private String accessTokenSecret;

    @Value("${book-library.jwt.access.expiration}")
    private long accessTokenExpiration;

    @Value("${book-library.jwt.refresh.secret}")
    private String refreshTokenSecret;

    @Value("${book-library.jwt.refresh.expiration}")
    private long refreshTokenExpiration;

    @Override
    public AuthTokens generateTokens(TokenClaims claims) {
        return new AuthTokens(
                generateToken(claims, TokenType.ACCESS),
                generateToken(claims, TokenType.REFRESH)
        );
    }

    //TODO: complete method
    @Override
    public boolean validateToken(String token, TokenType type) {
        return false;
    }

    @Override
    public Claims parseAllClaims(String token, TokenType type) {
        return Jwts.parser()
                .verifyWith(getSignKeyByType(type))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public <T> T parseClaim(String token, TokenType type, Function<Claims, T> handler) {
        return handler.apply(parseAllClaims(token, type));
    }

    @Override
    public String parseUsername(String token, TokenType type) {
        return parseClaim(token, type, Claims::getSubject);
    }

    @Override
    public UUID parseUserId(String token, TokenType type) {
        return UUID.fromString(parseClaim(token, type, Claims::getId));
    }

    @Override
    public boolean isExpired(String token, TokenType type) {
        return parseClaim(token, type, Claims::getExpiration).after(new Date());
    }

    private String generateToken(TokenClaims claims, TokenType type) {
        return Jwts.builder()
                .subject(claims.getUsername())
                .expiration(new Date(new Date().getTime() + getTokenExpirationByType(type)))
                .id(claims.getUserId())
                .signWith(getSignKeyByType(type))
                .compact();
    }

    private SecretKey getSignKeyByType(TokenType type) {
        final String secret = type.equals(TokenType.REFRESH) ? refreshTokenSecret : accessTokenSecret;
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private long getTokenExpirationByType(TokenType type) {
        return type.equals(TokenType.REFRESH) ? refreshTokenExpiration : accessTokenExpiration;
    }
}
