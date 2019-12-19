package com.smart.service;

import com.smart.pojo.Permission;

import java.util.List;

public interface PermissionService {
    //查询权限信息集合
    List<Permission> findAll();

    //添加权限信息
    void addPermission(Permission permission);

    //根据id删除权限信息
    void deletePermissionById(Long id);
}
