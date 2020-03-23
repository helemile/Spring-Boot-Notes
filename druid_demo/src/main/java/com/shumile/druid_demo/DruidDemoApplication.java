package com.shumile.druid_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DruidDemoApplication implements ApplicationRunner {
    @Autowired
    private  UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(DruidDemoApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        userDao.addUser();
        userDao.listData();
    }
}
