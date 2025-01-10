package com.kiyotaka.booklibraryapipractice.core.exception.handler;

import com.kiyotaka.booklibraryapipractice.core.exception.BookLibraryException;
import com.kiyotaka.booklibraryapipractice.core.model.ExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class BookLibraryExceptionHandler {
    @ExceptionHandler({BookLibraryException.class})
    public ResponseEntity<ExceptionResponse> handleBookLibraryException(BookLibraryException ex, HttpServletResponse response) {

        final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(ex.getLocalizedMessage())
                .status(ex.getStatus().value())
                .date(LocalDateTime.now())
                .exception(ex.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(exceptionResponse, ex.getStatus());
    }

    @ExceptionHandler({JwtException.class, ExpiredJwtException.class, SignatureException.class})
    public ResponseEntity<ExceptionResponse> handleJwtsException(JwtException exception) {
        final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .date(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .exception(exception.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException exception) {
        final ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .message(exception.getMessage())
                .date(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .exception(exception.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }
}