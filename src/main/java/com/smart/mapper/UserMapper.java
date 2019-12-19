package  com.smart.mapper;

import com.smart.pojo.UserInfo;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<UserInfo> {

    //查询用户详情 包括用户的信息，用户的角色信息 用户的权限信息
    UserInfo findUserById(Long id);

    //根据用户名称查询用户信息
    UserInfo findByUsername(String username);




}
