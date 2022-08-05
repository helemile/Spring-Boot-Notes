package com.wj.jwt.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserVo implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String token;

    private String refreshToken;


}
