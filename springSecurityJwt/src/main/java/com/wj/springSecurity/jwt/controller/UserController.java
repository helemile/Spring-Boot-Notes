package com.wj.springSecurity.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/common")
    public String common() {
        return "hello~ common";
    }

    @GetMapping("/admin")
    public String admin() {
        return "hello~ admin";
    }

}
