package com.wj.jwt.service;


import com.wj.jwt.entity.User;

public interface UserService {

    User findByUsername(String username);
}