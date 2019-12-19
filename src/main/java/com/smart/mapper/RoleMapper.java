package com.smart.mapper;

import com.smart.pojo.Permission;
import com.smart.pojo.Role;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {

    //删除用户角色
    void deleteUserRole(@Param("user_id") Long u_id);

    //添加用户角色
    void insertUserRole(@Param("role_id") Long id, @Param("user_id") Long u_id);

    //根据用户id查询用户角色
    Role findRoleByUserId(Long id);

    //查看角色详情
    Role findRoleByRoleId(Long id);

    //根据角色id查询可以添加的权限
    List<Permission> findPermissionByRoleId(Long id);

    //设置权限信息
    void settingPermission(@Param("per_id")Long per_id,@Param("role_id")Long role_id);

    //删除当前角色权限信息
    void deletePermission(@Param("per_id")Long per_id,@Param("role_id")Long role_id);

    //删除角色与用户的中间表
    void deleteRoleAndUser(Long role_id);
}
