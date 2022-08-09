package com.wj.springSecurity.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wj.springSecurity.jwt.vo.ResponseResult;
import com.wj.springSecurity.jwt.vo.UserVo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
public class JwtService implements InitializingBean{

    private static final String KEY = "wangjienihaowangjienihaowangjienihaowangjienihao";

    private static final String ISSUER = "jiege";

    private static final String AUTHORITIES_KEY = "auth";

    public static final Long TOKEN_EXSPIRE_TIME = 1000 * 60 * 60 * 10L;

    private Key key;


    /**
     * 创建 token
     * @param userVo 用户对象
     * @return token
     */
    public String createToken(UserVo userVo) {
        String authorities = userVo.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        return Jwts.builder()
                .setSubject(userVo.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + TOKEN_EXSPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 获取认证过的token对应的信息，包括用户以及用户对应的权限
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) {
        // 1-根据 token 和秘钥，解析出 JWT 的 claims 对象
        Claims claims =
                Jwts.parser()
                        .setSigningKey(KEY)
                        .parseClaimsJws(token)
                        .getBody();
        // 2-获取权限信息，并转换为集合类型
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        //3-得到用户对象 principal
        User principal = new User(claims.getSubject(), "", authorities);
         //4-得到认证 token 对象（UsernamePasswordAuthenticationToken 实现了 Authentication 接口，表示是通过用户名密码认证过的 token 认证信息）
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);

    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
}
