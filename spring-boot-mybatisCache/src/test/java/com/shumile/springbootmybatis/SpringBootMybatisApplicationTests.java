
package com.shumile.springbootmybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
@SpringBootTest
class SpringBootMybatisApplicationTests {
//    @Autowired
//    private UserMapperWithAnnotation userMapper;

//    @Test
//    public void oneSqlSession() throws IOException {
//        //配置文件的名称
//        String resource = "mybatis-configuration.xml";
//        //通过Mybatis包中的Resources对象很轻松的获取到配置文件
//        Reader reader = Resources.getResourceAsReader(resource);
//        //通过SqlSessionFactoryBuilder创建
//        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        SqlSession sqlSession = null;
//        try {
//            sqlSession = sqlSessionFactory.openSession();
//
//            // 执行第一次查询
//            User user = userMapper.findById(19l);
//
//            System.out.println("=============开始同一个 Sqlsession 的第二次查询============");
//            // 同一个 sqlSession 进行第二次查询
//            User user2 = userMapper.findById(19l);
//            Assert.assertEquals(user2, user);
//            System.out.printf("user:"+user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (sqlSession != null) {
//                sqlSession.close();
//            }
//        }
//    }

}

