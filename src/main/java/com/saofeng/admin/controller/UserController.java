package com.saofeng.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.saofeng.admin.pojo.UserInfo;
import com.saofeng.admin.service.impl.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/admin")
public class UserController {
    @Resource
    private UserService loginService;

    @RequestMapping("/login")
    public JSONObject login(@RequestBody UserInfo userInfo) {
        return loginService.selectByUser(userInfo);
    }
    @RequestMapping("/register")
    public JSONObject register(@RequestBody UserInfo userInfo) {
        return loginService.addUser(userInfo);
    }
    @RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
    public void getCaptcha() {

    }
}
