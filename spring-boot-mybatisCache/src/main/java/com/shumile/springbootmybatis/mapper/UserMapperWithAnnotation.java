package com.shumile.springbootmybatis.mapper;

import com.shumile.springbootmybatis.User;
import org.apache.ibatis.annotations.*;

/**
 * 使用注解方式定义数据库操作接口
 */
@Mapper
public interface UserMapperWithAnnotation {
    @Insert("insert into user (name,age, create_time, update_time)"
            + "values (#{name},#{age}, now(), now())")
    @Options(useGeneratedKeys = true,keyColumn="id", keyProperty="id")
    int save(User user);

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            //@Result(column = "create_time", property = "createTime"),
            //@Result(column = "update_time", property = "updateTime"),
            //配置项map-underscore-to-camel-case = true 可以实现一样的效果
    })

    User findById(@Param("id") Long id);

}