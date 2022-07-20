package com.wj.activiti.reposity;

import com.wj.activiti.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {


    /**
     * 查询用户的权限根据用户查询权限
     *
     * @return
     */
    @Select("select * from permission")
    List<Permission> findAllPermission();
}
