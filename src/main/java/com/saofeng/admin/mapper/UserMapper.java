package com.saofeng.admin.mapper;

import com.saofeng.admin.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper {
    UserInfo selectByUsername(@Param("username") String username);
    Integer addUser(UserInfo userInfo);
}
