package com.wj.springSecurity.jwt.reposity;

import com.wj.springSecurity.jwt.entity.Permission;
import com.wj.springSecurity.jwt.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper  {

    /**
     * 根据用户名称查询
     *
     * @param userName
     * @return
     */
    @Select("select * from user where username = #{userName}")
    User findByUsername(@Param("userName") String userName);

    /**
     * 查询用户的权限根据用户查询权限
     *
     * @param userName
     * @return
     */
    @Select(" SELECT d.*\n" +
            "from user a,user_role b,role_permission c,permission d\n" +
            "WHERE \n" +
            "a.id = b.user_id\n" +
            "and b.role_id = c.role_id\n" +
            "and c.permission_id = d.id\n" +
            "and \n" +
            "a.username= #{userName};")
    List<Permission> findPermissionByUsername(@Param("userName") String userName);
}
