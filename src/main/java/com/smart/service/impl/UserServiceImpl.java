package com.smart.service.impl;

import com.smart.enums.ExceptionEnum;
import com.smart.exception.FactoryException;
import com.smart.mapper.RoleMapper;
import com.smart.mapper.UserMapper;
import com.smart.pojo.Role;
import com.smart.pojo.UserInfo;
import com.smart.service.UserService;
import com.smart.utils.CodecUtils;
import com.smart.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public List<UserInfo> findAll() {
        List<UserInfo> infoList = userMapper.selectAll();
        if (CollectionUtils.isEmpty(infoList)) {
            throw new FactoryException(ExceptionEnum.USER_NOT_FOUND);
        }
        return infoList;
    }

    @Override
    @Transactional
    public void addUser(UserInfo info) {
        //设置id
        info.setId(System.currentTimeMillis());
        //设置创建时间
        info.setCreate_time(DateUtils.getCurrentTime());
        //加密密码
        String newPassword = CodecUtils.passwordBcryptEncode(info.getPassword());
        //设置新密码
        info.setPassword(newPassword);
        //保存到数据库
        int i = userMapper.insertSelective(info);
        if(i!=1)
        {
            throw new FactoryException(ExceptionEnum.INSERT_USER_INVALID);
        }
    }

    @Override
    @Transactional
    public void deleteUserByID(Long  id) {
        //根据用户的id删除用户的角色
        roleMapper.deleteUserRole(id);
        //删除用户
        int i = userMapper.deleteByPrimaryKey(id);
        if(i!=1)
        {
            throw new FactoryException(ExceptionEnum.DELETE_USER_ERROR);
        }
    }

    @Override
    public UserInfo findUserById(Long id) {
        UserInfo user = userMapper.findUserById(id);
        if(user==null)
        {
            UserInfo info = userMapper.selectByPrimaryKey(id);
            if(info==null)
            {
                throw new FactoryException(ExceptionEnum.USER_NOT_FOUND);
            }
            return info;
        }
        return user;
    }

    @Override
    public UserInfo findUserByRole(Long id) {
        UserInfo info = userMapper.selectByPrimaryKey(id);
        if(info==null)
        {
            throw new  FactoryException(ExceptionEnum.USER_NOT_FOUND);
        }
        Role role = roleMapper.findRoleByUserId(id);
        info.setRole(role);
        return info;
    }
}
