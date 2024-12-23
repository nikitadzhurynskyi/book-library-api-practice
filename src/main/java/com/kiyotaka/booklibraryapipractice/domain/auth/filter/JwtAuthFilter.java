package com.kiyotaka.booklibraryapipractice.domain.auth.filter;

import com.kiyotaka.booklibraryapipractice.domain.auth.model.TokenType;
import com.kiyotaka.booklibraryapipractice.domain.auth.service.JwtService;
import com.kiyotaka.booklibraryapipractice.domain.user.entity.UserEntity;
import com.kiyotaka.booklibraryapipractice.domain.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;

    public JwtAuthFilter(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String accessToken = parseTokenFromRequest(request);
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String username = jwtService.parseUsername(accessToken, TokenType.ACCESS);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserEntity userEntity = userService.findByOrThrow(username);

            if (jwtService.validateAccessToken(accessToken, userEntity)) {
                final var authenticationToken = new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private String parseTokenFromRequest(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }
        return authorizationHeader.substring(7);
    }
}
