package com.wj.springSecurity.jwt.service;

import com.wj.springSecurity.jwt.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService<T extends User>  extends UserDetailsService {

     User findByUsername(String username);

}