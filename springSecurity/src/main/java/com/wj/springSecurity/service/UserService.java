package com.wj.springSecurity.service;

import com.wj.springSecurity.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService<T extends User>  extends UserDetailsService {


}