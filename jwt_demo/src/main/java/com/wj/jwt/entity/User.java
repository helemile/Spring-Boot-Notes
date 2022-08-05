package com.wj.jwt.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Long id;

    private String username;

    private String password;

    private String token;

    private String refreshToken;
}
