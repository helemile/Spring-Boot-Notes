package com.shumile.springbootmybatis;

import com.shumile.springbootmybatis.mapper.UserMapperWithAnnotation;
import com.shumile.springbootmybatis.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Slf4j
@MapperScan("com.shumile.springbootmybatis.mapper")
@EnableTransactionManagement
public class SpringBootMybatisApplication implements ApplicationRunner {

    @Autowired
    private UserMapperWithAnnotation userMapperWithAnnotation;
    @Autowired
    private UserMapper userMapper;


    public static void main(String[] args) {
        SpringApplication.run(SpringBootMybatisApplication.class, args);
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
          testCache();
    }

    public void testSecondCache1() throws IOException {
        //配置文件的名称
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);

        UserMapper userMapper = sqlSession1.getMapper(UserMapper.class);

        System.out.println("userMapperWithXml读取数据: " + userMapper.getAllUserList());

        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        System.out.println("userMapperWithXml2 读取数据: " + userMapper2.getAllUserList());
    }


    public void testSecondCache2() throws IOException {
        //配置文件的名称
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);

        UserMapper userMapper = sqlSession1.getMapper(UserMapper.class);

        System.out.println("userMapperWithXml读取数据: " + userMapper.getAllUserList());

        sqlSession1.commit();


        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        System.out.println("userMapperWithXml3 读取数据: " + userMapper2.getAllUserList());
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-configuration.xml";
        //通过Mybatis包中的Resources对象很轻松的获取到配置文件
        Reader reader = Resources.getResourceAsReader(resource);
        //通过SqlSessionFactoryBuilder创建
        return new SqlSessionFactoryBuilder().build(reader);
    }


    public void testSecondCache3() throws IOException {
        //配置文件的名称
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);

        UserMapper userMapper = sqlSession1.getMapper(UserMapper.class);

        System.out.println("userMapperWithXml读取数据: " + userMapper.getAllUserList());

        sqlSession1.commit();

        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

        Map<String, Object> map = new HashMap<>();
        map.put("age",20);
        userMapper2.updateUser(map);

        sqlSession2.commit();

        SqlSession sqlSession3 = sqlSessionFactory.openSession(true);
        UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);

        System.out.println("userMapperWithXml3 读取数据: " + userMapper3.getAllUserList());
    }

    /**
     * 测试一级缓存
     */
    public void testCache(){
        User user = userMapperWithAnnotation.findById(33L);
        log.info("Find User: {}", user);

        User user2 = userMapperWithAnnotation.findById(33L);
        log.info("Find User2: {}", user2);
    }



}
