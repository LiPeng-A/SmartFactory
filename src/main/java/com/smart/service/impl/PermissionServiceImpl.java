package com.smart.service.impl;

import com.smart.enums.ExceptionEnum;
import com.smart.exception.FactoryException;
import com.smart.mapper.PermissionMapper;
import com.smart.pojo.Permission;
import com.smart.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findAll() {
        List<Permission> permissions = permissionMapper.selectAll();
        if (CollectionUtils.isEmpty(permissions)) {
            throw new FactoryException(ExceptionEnum.PERMISSION_NOT_FOUND);
        }
        return permissions;
    }

    @Override
    @Transactional
    public void addPermission(Permission permission) {
        permission.setId(System.currentTimeMillis());
        int insert = permissionMapper.insert(permission);
        if(insert!=1)
        {
            throw new FactoryException(ExceptionEnum.PERMISSION_ADD_ERROR);
        }
    }

    @Override
    @Transactional
    public void deletePermissionById(Long id) {
        int i = permissionMapper.deleteByPrimaryKey(id);
        if(i!=1)
        {
            throw new FactoryException(ExceptionEnum.PERMISSION_DELETE_ERROR);
        }
    }
}
