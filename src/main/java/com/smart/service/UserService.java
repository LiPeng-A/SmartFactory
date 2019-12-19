package com.smart.service;

import com.smart.pojo.Role;
import com.smart.pojo.UserInfo;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserService {
    //查询所有用户信息
    List<UserInfo> findAll();

    //创建用户
    void addUser(UserInfo info);

    //根据用户id删除用户
    void deleteUserByID(Long id);

    //根据id查询用户
    UserInfo findUserById(Long id);

    //根据用户id查询用户拥有的角色
    UserInfo findUserByRole(Long id);
}
