package com.smart.mapper;

import com.smart.pojo.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PermissionMapper extends Mapper<Permission> {

    //删除角色与权限中间表
    void deleteRolePermission(@Param("id") Long id);
}
