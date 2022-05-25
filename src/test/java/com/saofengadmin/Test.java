package com.saofengadmin;

import com.alibaba.fastjson.JSONObject;
import com.saofeng.admin.utils.JwtUtils;

public class Test {
    @org.junit.jupiter.api.Test
    public void demo(){
//        String token = JwtUtils.createToken(123, "saofeng");
//        System.out.println(token);
        String token1 ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NTM1NzcxOTgsInV1aWQiOjEwLCJ1c2VybmFtZSI6InNhb2Zlbmc2NjYifQ.grJ7kfgroneawaKkXfTdgYW6CjyD4-pBMmpQYSPlOXo";
        JSONObject verify = JwtUtils.verify(token1);
        System.out.println(verify);
    }

}
