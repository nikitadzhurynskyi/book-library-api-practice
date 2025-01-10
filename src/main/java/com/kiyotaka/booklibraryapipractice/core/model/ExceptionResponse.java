package com.kiyotaka.booklibraryapipractice.core.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
public class ExceptionResponse {
    private String message;

    private int status;

    private String exception;

    private LocalDateTime date;
}