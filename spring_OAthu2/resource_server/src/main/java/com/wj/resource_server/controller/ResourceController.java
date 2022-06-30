package com.wj.resource_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rs")
public class ResourceController {
    @GetMapping("/get_resource")
    public String hello() {
        return "我是被保护的资源接口";
    }
}
