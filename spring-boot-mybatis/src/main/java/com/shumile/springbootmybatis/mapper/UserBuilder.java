package com.shumile.springbootmybatis.mapper;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class UserBuilder  implements ProviderMethodResolver {

    public String deleteUserSql() {
        return new SQL() {{
            DELETE_FROM("user");
            WHERE("ID = ${id}");
        }}.toString();
    }
    public static String selectUserById() {
        return new SQL()
                .SELECT("name,age,create_time,update_time")
                .FROM("user")
                .WHERE("id = #{id}")
                .toString();
    }
}
