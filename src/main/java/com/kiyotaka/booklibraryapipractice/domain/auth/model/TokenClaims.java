package com.kiyotaka.booklibraryapipractice.domain.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TokenClaims {
    private String username;

    private String userId;

    // TODO: change to role class/enum
    private String userRole;
}
