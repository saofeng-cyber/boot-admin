package com.saofeng.admin.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.saofeng.admin.pojo.UserInfo;

public interface UserService {
    JSONObject selectByUser(UserInfo userInfo);
    JSONObject addUser(UserInfo userInfo);
    JSONObject updateToken(Integer uid,String token);
}
