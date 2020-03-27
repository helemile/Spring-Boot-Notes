package com.shumile.springbootmybatis;

import com.shumile.springbootmybatis.mapper.UserMapperWithAnnotation;
import com.shumile.springbootmybatis.mapper.UserMapperWithBuilder;
import com.shumile.springbootmybatis.mapper.UserMapperWithXml;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
@MapperScan("com.shumile.springbootmybatis.mapper")
@EnableTransactionManagement
public class SpringBootMybatisApplication implements ApplicationRunner {

    @Autowired
    private UserMapperWithAnnotation userMapperWithAnnotation;
    @Autowired
    private UserMapperWithXml userMapperWithXml;

    @Autowired
    private UserMapperWithBuilder userMapperWithBuilder;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        //testByMapper();
        testCache();
        //testByXml();
        //testBuilder();

    }
    private void testBuilder(){
        userMapperWithBuilder.selectUserById(18l);
    }
   //@Transactional
    public void testCache(){
        User user = userMapperWithAnnotation.findById(32L);
        log.info("Find User: {}", user);

        User user2 = userMapperWithAnnotation.findById(32L);
        log.info("Find User2: {}", user2);
    }

    private void testByXml() {
        Map<String, Object> map = new HashMap<>();
        map.put("name","小米");
        map.put("age",17);
        List<User> userList = userMapperWithXml.getUserList(map);
        userList.forEach(user->log.info("user:{}",user));
    }

    private void testByMapper() {
        User c = User.builder().name("小米").age(17)
                .createTime(new Date())
                .updateTime(new Date()).build();
        int count = userMapperWithAnnotation.save(c);
        log.info("Save {} User: {}", count, c);

        c = userMapperWithAnnotation.findById(c.getId());
        log.info("Find User: {}", c);

    }


}
