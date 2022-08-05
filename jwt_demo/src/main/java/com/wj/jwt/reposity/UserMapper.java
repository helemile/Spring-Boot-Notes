package com.wj.jwt.reposity;


import com.wj.jwt.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper  {

    /**
     * 根据用户名称查询
     *
     * @param userName
     * @return
     */
    @Select(" select * from user where username = #{userName}")
    User findByUsername(@Param("userName") String userName);


}
