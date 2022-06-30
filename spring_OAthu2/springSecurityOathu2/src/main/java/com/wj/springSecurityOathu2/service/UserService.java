package com.wj.springSecurityOathu2.service;

import com.wj.springSecurityOathu2.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService<T extends User>  extends UserDetailsService {


}