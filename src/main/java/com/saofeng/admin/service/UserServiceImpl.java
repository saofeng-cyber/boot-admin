package com.saofeng.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.saofeng.admin.mapper.UserMapper;
import com.saofeng.admin.pojo.UserInfo;
import com.saofeng.admin.service.impl.UserService;
import com.saofeng.admin.utils.DigestPassword;
import com.saofeng.admin.utils.JwtUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public JSONObject selectByUser(UserInfo userInfo) {
        JSONObject jsonObject = new JSONObject();
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        UserInfo result = userMapper.selectByUsername(username);
        if (result == null) {
            jsonObject.put("success", "failed");
            jsonObject.put("msg", "用户不存在,请先注册");
            return jsonObject;
        }
        if ("".equals(username)) {
            jsonObject.put("success", "failed");
            jsonObject.put("msg", "用户名不能为空");
            return jsonObject;
        }
        if ("".equals(password)) {
            jsonObject.put("success", "failed");
            jsonObject.put("msg", "密码不能为空");
            return jsonObject;
        }
        String salt = result.getSalt();
        String md5password = DigestPassword.getMd5Password(salt, password);
        if (!md5password.equals(result.getPassword())) {
            jsonObject.put("success", "failed");
            jsonObject.put("msg", "密码不正确，请重新输入");
            return jsonObject;
        }
        System.out.println(md5password);
        Map<String,String> map = new HashMap<>();
        map.put("username", username);
        String token = JwtUtils.createToken(map);
        result.setToken(token);
        Date date = new Date();
        userInfo.setCreateTime(date);
        jsonObject.put("success", "true");
        jsonObject.put("userInfo", result);
        jsonObject.put("msg", "登录成功");
        return jsonObject;
    }

    @Override
    public JSONObject addUser(UserInfo userInfo) {
        String salt = UUID.randomUUID().toString().toUpperCase();
        String password = userInfo.getPassword();
        userInfo.setPassword(DigestPassword.getMd5Password(salt, password));
        JSONObject jsonObject = new JSONObject();
        Date date = new Date();
        userInfo.setSalt(salt);
        userInfo.setCreateTime(date);
        Integer integer = userMapper.addUser(userInfo);
        if (integer >= 1) {
            jsonObject.put("success", "true");
            jsonObject.put("userInfo", userInfo);
        }
        return jsonObject;
    }
}
