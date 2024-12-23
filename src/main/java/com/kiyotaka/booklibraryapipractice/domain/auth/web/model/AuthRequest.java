package com.kiyotaka.booklibraryapipractice.domain.auth.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AuthRequest {
    private String email;

    private String password;
}
