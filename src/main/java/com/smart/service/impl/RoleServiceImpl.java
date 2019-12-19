package com.smart.service.impl;

import com.smart.enums.ExceptionEnum;
import com.smart.exception.FactoryException;
import com.smart.mapper.PermissionMapper;
import com.smart.mapper.RoleMapper;
import com.smart.pojo.Permission;
import com.smart.pojo.Role;
import com.smart.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Role> findAll() {

        List<Role> roles = roleMapper.selectAll();
        if (CollectionUtils.isEmpty(roles)) {
            throw new FactoryException(ExceptionEnum.ROLE_NOT_FOUND);
        }
        return roles;
    }

    @Override
    @Transactional
    public void settingRole(Long id, Long u_id) {

        //删除之前的角色
        roleMapper.deleteUserRole(u_id);
        //添加当前的角色
        roleMapper.insertUserRole(id, u_id);
    }

    @Override
    @Transactional
    public void deleteRole(Long role_id) {

        //先删除权限信息
        permissionMapper.deleteRolePermission(role_id);
        //再删除用户与角色的中间表
        roleMapper.deleteRoleAndUser(role_id);
        //再删除角色信息
        int i = roleMapper.deleteByPrimaryKey(role_id);
        if (i != 1) {
            throw new FactoryException(ExceptionEnum.DELETE_ROLE_ERROR);
        }

    }

    @Override
    public Role findAllShow(Long id) {
        Role role = roleMapper.findRoleByRoleId(id);
        return role;
    }

    @Override
    public Role findPermission(Long id) {

        List<Permission> permissionList = roleMapper.findPermissionByRoleId(id);
        Role role = roleMapper.selectByPrimaryKey(id);
        role.setPermissions(permissionList);

        return role;
    }

    @Transactional
    @Override
    public void settingPermission(Long per_id, Long role_id) {
        roleMapper.settingPermission(per_id,role_id);
    }

    @Override
    @Transactional
    public void delPermission(Long per_id, Long roleId) {
        roleMapper.deletePermission(per_id,roleId);
    }

    @Override
    public void addRole(Role role) {
        role.setId(System.currentTimeMillis());
        int insert = roleMapper.insert(role);
        if(insert!=1){
            throw new FactoryException(ExceptionEnum.ROLE_ADD_ERROR);
        }
    }
}
