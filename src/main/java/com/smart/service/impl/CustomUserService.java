package com.smart.service.impl;

import com.smart.enums.ExceptionEnum;
import com.smart.exception.FactoryException;
import com.smart.mapper.RoleMapper;
import com.smart.mapper.UserMapper;
import com.smart.pojo.Role;
import com.smart.pojo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("查找到用户信息:{}", s);
        //用户验证
        UserInfo user1 = new UserInfo();
        user1.setUsername(s);
        UserInfo info = userMapper.findByUsername(s);
        if (info == null) {
            throw new FactoryException(ExceptionEnum.USER_NOT_FOUND);
        }
        //授权
        User user = new User(info.getUsername(),
                info.getPassword(), info.getStatus() == 0 ? false : true, true, true, true, getAuthority(info.getRole()));

        return user;
    }

    /**
     * 授权管理
     *
     * @return
     */
    public List<SimpleGrantedAuthority> getAuthority(Role role) {
        String roleName = null;
        //获取用户权限
        roleName = role.getRole_name();

        List<SimpleGrantedAuthority> grantAuthority = new ArrayList<SimpleGrantedAuthority>();

        grantAuthority.add(new SimpleGrantedAuthority("ROLE_" + roleName));

        return grantAuthority;

    }
}
