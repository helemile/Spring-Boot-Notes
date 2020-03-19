package com.shumile.springbootmybatis.mapper;

import com.shumile.springbootmybatis.User;
import org.apache.ibatis.annotations.*;

/**
 * 使用语句构造器方式定义数据库操作接口
 */
@Mapper
public interface UserMapperWithBuilder {

    @SelectProvider(type = UserBuilder.class)

    User selectUserById(long id);

}