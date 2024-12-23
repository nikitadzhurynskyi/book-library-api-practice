package com.kiyotaka.booklibraryapipractice.domain.auth.service;

import com.kiyotaka.booklibraryapipractice.domain.auth.model.AuthTokens;
import com.kiyotaka.booklibraryapipractice.domain.auth.web.model.AuthRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.web.model.UserResponse;

public interface AuthService {
    UserResponse register(AuthRequest authRequest);

    AuthTokens login(AuthRequest authRequest);
}
