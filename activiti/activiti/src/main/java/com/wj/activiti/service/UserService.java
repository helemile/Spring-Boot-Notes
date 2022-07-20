package com.wj.activiti.service;

import com.wj.activiti.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService<T extends User>  extends UserDetailsService {


}