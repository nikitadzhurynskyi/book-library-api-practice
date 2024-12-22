package com.kiyotaka.booklibraryapipractice.domain.auth.web.controller;

import com.kiyotaka.booklibraryapipractice.domain.auth.service.AuthService;
import com.kiyotaka.booklibraryapipractice.domain.auth.web.model.AuthRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.web.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<>(authService.register(authRequest), HttpStatus.CREATED);
    }
}
