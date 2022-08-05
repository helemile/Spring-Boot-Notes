package com.wj.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.wj.jwt.vo.ResponseResult;
import com.wj.jwt.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtService {
    //加密秘钥
    private static final String SECRET_KEY = "wangjienihao";
    // 签发人
    private static final String ISSUER = "jiege";
    // token 过期时间
    public static final Long TOKEN_EXSPIRE_TIME = 1000 * 60 * 60 * 10L;

    /**
     * 生成 token
     * @param userVo 用户信息
     * @return
     */
    public String token(UserVo userVo) {
        //1-确定加密算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        Date now = new Date();
        //2-开始创建和生成 token
        return JWT.create()
                .withIssuedAt(now)
                .withIssuer(ISSUER)
                .withExpiresAt(new Date(now.getTime() + TOKEN_EXSPIRE_TIME))//token 的过期时间
                .withClaim("username", userVo.getUsername())
                .withClaim("password", userVo.getPassword())
                .sign(algorithm);
    }

    /**
     * 校验用户名
     * @param token token
     * @param username 用户名
     * @return
     */
    public ResponseResult verifyUsername(String token,String username){
       log.info("verify jwt-username - {}",username);
        ResponseResult responseResult = new ResponseResult();
       try {
            //1-定义算法
           Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
           //2-进行校验
           JWTVerifier jwtVerifier = JWT.require(algorithm)
                   .withIssuer(ISSUER)
                   .withClaim("username", username)
                   .build();
           jwtVerifier.verify(token);
       } catch (Exception ex){
           responseResult.setStatus(-1);
           responseResult.setMessage("失败！");
           log.error("auth verify fail：{}",ex.getMessage());
       }
        return responseResult;

    }
}
