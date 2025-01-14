package com.kiyotaka.booklibraryapipractice.domain.auth.service;

import com.kiyotaka.booklibraryapipractice.core.exception.BookLibraryException;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.AuthTokens;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenClaims;
import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenType;
import com.kiyotaka.booklibraryapipractice.domain.auth.web.model.AuthRequest;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.mapper.UserMapper;
import com.kiyotaka.booklibraryapipractice.domain.user.service.UserService;
import com.kiyotaka.booklibraryapipractice.domain.user.web.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserService userService,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse register(AuthRequest authRequest) {
        if (userService.isExists(authRequest.getEmail())) {
            throw new BookLibraryException("User already registered.", HttpStatus.FORBIDDEN);
        }
        authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        final UserEntity userEntity = userService.create(userMapper.mapAuthRequestToUserDto(authRequest));
        return userMapper.mapUserEntityToUserResponse(userEntity);
    }

    @Override
    public AuthTokens login(AuthRequest authRequest) {
        final UserEntity userEntity = userService.findByOrThrow(authRequest.getEmail());

        if (!passwordEncoder.matches(authRequest.getPassword(), userEntity.getPassword())) {
            throw new BookLibraryException("Invalid password.", HttpStatus.UNAUTHORIZED);
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                authRequest.getPassword(),
                userEntity.getAuthorities()));

        return jwtService.generateTokens(new TokenClaims(userEntity.getEmail(), userEntity.getId()));
    }

    @Override
    public AuthTokens refreshTokens(String refreshToken) {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw new BookLibraryException("Invalid refresh token.", HttpStatus.UNAUTHORIZED);
        }
        final UserEntity userEntity = userService.findByOrThrow(
                jwtService.parseUserId(refreshToken, TokenType.REFRESH));

        return jwtService.generateTokens(new TokenClaims(userEntity.getEmail(), userEntity.getId()));
    }
}
