package com.shumile.springbootjpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
public class SpringBootJpaApplication implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        User user = User.builder()
                .name("小米")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        userRepository.save(user);
        log.info("one user added :{}",user);

        Iterable<User> users = userRepository.findAll();
        users.forEach(System.out::println);

        //调用自定义查询方法
//        List<User> userList = userRepository.findByNameOrderByCreateTimeDesc("小米");
//        log.info("userList:{}",userList);

    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJpaApplication.class, args);
    }
}
