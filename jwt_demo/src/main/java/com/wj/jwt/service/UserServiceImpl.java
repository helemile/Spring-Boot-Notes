package com.wj.jwt.service;

import com.wj.jwt.entity.User;
import com.wj.jwt.reposity.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl implements UserService  {

    @Resource
    private UserMapper userMapper;


    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);

    }
}
