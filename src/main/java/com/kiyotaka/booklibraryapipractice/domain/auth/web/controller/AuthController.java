package com.kiyotaka.booklibraryapipractice.domain.auth.web.controller;

import com.kiyotaka.booklibraryapipractice.domain.auth.model.AuthTokens;
import com.kiyotaka.booklibraryapipractice.domain.auth.service.AuthService;
import com.kiyotaka.booklibraryapipractice.domain.auth.web.model.AuthRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.web.model.UserResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";

    @Value("${book-library.jwt.refresh.expiration}")
    private int refreshTokenCookieMaxAgeInMillis;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.register(authRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthTokens login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        final AuthTokens authTokens = authService.login(authRequest);
        addTokenToCookies(authTokens.getRefreshToken(), response);

        return authTokens;
    }

    @GetMapping("/refresh")
    public AuthTokens refresh(@CookieValue(REFRESH_TOKEN_COOKIE_NAME) String refreshToken, HttpServletResponse response) {
        final AuthTokens authTokens = authService.refreshTokens(refreshToken);
        addTokenToCookies(authTokens.getRefreshToken(), response);

        return authTokens;
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        removeTokenFromCookies(response);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-authentication")
    public String checkAuthentication(@AuthenticationPrincipal UserEntity userEntity) {
        return "Hello, " + userEntity.getUsername();
    }

    private void addTokenToCookies(String token, HttpServletResponse response) {
        final Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, token);

        cookie.setHttpOnly(true);
        cookie.setMaxAge(refreshTokenCookieMaxAgeInMillis / 1000);

        response.addCookie(cookie);
    }

    private void removeTokenFromCookies(HttpServletResponse response) {
        final Cookie cookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
