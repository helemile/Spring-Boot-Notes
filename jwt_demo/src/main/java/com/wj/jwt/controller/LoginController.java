package com.wj.jwt.controller;


import cn.hutool.core.util.StrUtil;
import com.wj.jwt.entity.User;
import com.wj.jwt.service.JwtService;
import com.wj.jwt.service.UserService;
import com.wj.jwt.util.MD5Util;
import com.wj.jwt.vo.ResponseResult;
import com.wj.jwt.vo.UserVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Resource
    private JwtService jwtService;

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, UserVo> redisTemplate;


    @PostMapping("/session/login")
    public String sessionLogin(String username,String password) throws Exception {
      //1-校验用户名密码是否为空
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            throw new Exception("用户名或密码不能为空！");
        }

        // 2-根据用户查询用户是否存在
        User user = userService.findByUsername(username);
        if (user == null){
            throw new Exception("用户名或密码有误！");
        }

        //2-对密码进行加密加盐处理
        password = MD5Util.md5slat(password);
        if (!password.equalsIgnoreCase(user.getPassword())){
            throw new Exception("用户名或密码有误！");
        }
        return "success!";
    }


    @PostMapping("/jwt/login")
    public ResponseResult login(String username,String password) throws Exception {
        //1-校验用户名密码是否为空
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            throw new Exception("用户名或密码不能为空！");
        }

        // 2-根据用户查询用户是否存在
        User user = userService.findByUsername(username);
        if (user == null){
            throw new Exception("用户名或密码有误！");
        }
        //3-验证用户名密码
        password = MD5Util.md5slat(password);
        if (!password.equalsIgnoreCase(user.getPassword())){
            throw new Exception("用户名或密码有误！");
        }
        UserVo userVo =  UserVo.builder().build();
        userVo.setId(user.getId());
        userVo.setUsername(username);
        userVo.setPassword(password);
        String token = jwtService.token(userVo);
        userVo.setToken(token);
        userVo.setRefreshToken(UUID.randomUUID().toString());
        //同时存储用户到 redis 中
        redisTemplate.opsForValue().set(token,userVo,JwtService.TOKEN_EXSPIRE_TIME, TimeUnit.SECONDS);
        return new ResponseResult(userVo);
    }

    @PostMapping("refreshToken")
    public ResponseResult refreshToken(@RequestParam("token") String oldToken){
       //1-获取 token
        UserVo userVo = redisTemplate.opsForValue().get(oldToken);
        if (userVo == null){
            return new ResponseResult(500,"user not found!",null);
        }

        String token = jwtService.token(userVo);
        userVo.setToken(token);
        userVo.setRefreshToken(UUID.randomUUID().toString());
        //同时存储用户到 redis 中
        redisTemplate.delete(oldToken);
        redisTemplate.opsForValue().set(token,userVo,JwtService.TOKEN_EXSPIRE_TIME +10000, TimeUnit.SECONDS);
        return new ResponseResult(userVo);

    }
}
