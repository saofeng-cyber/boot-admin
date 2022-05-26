package com.saofeng.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.saofeng.admin.mapper.UserMapper;
import com.saofeng.admin.pojo.UserInfo;
import com.saofeng.admin.service.impl.UserService;
import com.saofeng.admin.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public JSONObject selectByUser(UserInfo userInfo) {
        JSONObject jsonObject = new JSONObject();
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        UserInfo result = userMapper.selectByUsername(username);
        if (result == null) {
            jsonObject.put("success", "failed");
            jsonObject.put("msg", "用户不存在,请先注册");
            log.error(String.valueOf(jsonObject));
            return jsonObject;
        }
        if (username.isBlank() || password.isBlank()) {
            jsonObject.put("success", "failed");
            jsonObject.put("msg", "用户名或密码不能为空");
            log.error(String.valueOf(jsonObject));
            return jsonObject;
        }
        Integer uuid = result.getUid();
        Date date = new Date();
        String token = JwtUtils.createToken(uuid, username);
//        stringRedisTemplate.opsForValue().set("token", token, 60, TimeUnit.SECONDS);
        updateToken(uuid, token);
        result.setToken(token);
        userInfo.setCreateTime(date);
        jsonObject.put("success", "true");
        jsonObject.put("userInfo", result);
        jsonObject.put("msg", "登录成功");
        log.info(String.valueOf(jsonObject));
        return jsonObject;
    }

    @Override
    public JSONObject addUser(UserInfo userInfo) {
        String salt = UUID.randomUUID().toString().toUpperCase();
        JSONObject jsonObject = new JSONObject();
        Date date = new Date();
        userInfo.setSalt(salt);
        userInfo.setCreateTime(date);
        Integer integer = userMapper.addUser(userInfo);
        if (integer >= 1) {
            jsonObject.put("success", "true");
            jsonObject.put("userInfo", userInfo);
        }
        log.error(String.valueOf(jsonObject));
        return jsonObject;
    }

    @Override
    public JSONObject updateToken(Integer uuid, String token) {
        Integer integer = userMapper.updateToken(uuid, token);
        JSONObject jsonObject = new JSONObject();
        if (integer > 0) {
            jsonObject.put("success", "true");
            jsonObject.put("msg", "token更新成功");
            log.info(String.valueOf(jsonObject));
            return jsonObject;
        }
        jsonObject.put("success", "failed");
        jsonObject.put("msg", "token更新失败");
        log.error(String.valueOf(jsonObject));
        return jsonObject;
    }
}
