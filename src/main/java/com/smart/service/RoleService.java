package com.smart.service;

import com.smart.pojo.Permission;
import com.smart.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    //查询角色集合
    List<Role> findAll();

    //设置角色
    void settingRole(Long id, Long u_id);

    //删除角色信息
    void deleteRole(Long user_id);

    //显示角色详情
    Role findAllShow(Long id);

    //查询可以设置的权限信息
    Role findPermission(Long id);

    //设置权限
    void settingPermission(Long per_id, Long role_id);

    //删除当前角色的权限
    void delPermission(Long per_id, Long roleId);

    //添加角色信息
    void addRole(Role role);
}
