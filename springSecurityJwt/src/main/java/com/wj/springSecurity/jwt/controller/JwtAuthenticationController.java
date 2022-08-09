package com.wj.springSecurity.jwt.controller;

import cn.hutool.core.util.StrUtil;
import com.wj.springSecurity.jwt.entity.User;
import com.wj.springSecurity.jwt.service.JwtService;
import com.wj.springSecurity.jwt.service.UserService;
import com.wj.springSecurity.jwt.utils.MD5Util;
import com.wj.springSecurity.jwt.vo.ResponseResult;
import com.wj.springSecurity.jwt.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Resource
    private UserService<User> userService;

    @Resource
    private JwtService jwtService;

    /**
     * 登录接口 - 用于生成 token
     * @param username 用户名
     * @param password 密码
     * @return
     * @throws Exception
     */
    @PostMapping("/authenticate")
    public ResponseResult login(String username, String password) throws Exception {
        //1-校验用户名密码是否为空
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            throw new Exception("用户名或密码不能为空！");
        }

        // 2-根据用户查询用户是否存在
        UserDetails user = userService.loadUserByUsername(username);
        if (user == null){
            throw new Exception("用户名或密码有误！");
        }
        //3-验证用户名密码
        password = MD5Util.md5slat(password);
        if (!password.equalsIgnoreCase(user.getPassword())){
            throw new Exception("用户名或密码有误！");
        }

        UserVo userVo =  UserVo.builder().build();
        userVo.setUsername(username);
        userVo.setPassword(password);
        userVo.setAuthorities(user.getAuthorities());
        //4- 生成 token
        String token = jwtService.createToken(userVo);
        userVo.setToken(token);
        userVo.setRefreshToken(UUID.randomUUID().toString());

        return new ResponseResult(userVo);
    }
}