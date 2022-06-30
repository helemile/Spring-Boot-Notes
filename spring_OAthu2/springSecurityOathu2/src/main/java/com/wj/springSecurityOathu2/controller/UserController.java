package com.wj.springSecurityOathu2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/hello")
    public String hello(@RequestParam("code") String code) {
        return "hello~  授权码 code 为：" + code;
    }

}
