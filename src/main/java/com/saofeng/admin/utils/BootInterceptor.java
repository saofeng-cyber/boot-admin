package com.saofeng.admin.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class BootInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        response.setContentType("application/json;charset=utf-8");
//        PrintWriter writer = response.getWriter();
//        JSONObject jsonObject = new JSONObject();
        if (token.isBlank()){
//            jsonObject.put("msg","token不存在,请先获取token");
//            writer.print(jsonObject);
            return false;
        }
        else {
            JSONObject verify = JwtUtils.verify(token);
            if ("true".equals(verify.get("msg"))){
                return true;
            }
            else {
//                jsonObject.put("msg",verify.get("msg"));
//                writer.print(jsonObject);
                return false;
            }
        }
    }
}
