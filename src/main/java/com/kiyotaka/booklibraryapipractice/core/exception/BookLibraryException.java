package com.kiyotaka.booklibraryapipractice.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class BookLibraryException extends RuntimeException {
    private HttpStatus status;

    public BookLibraryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}