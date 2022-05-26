package com.saofeng.admin.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class BootInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        response.setContentType("application/json;charset=utf-8");
        if (token.isBlank()){
            JSONObject jsonObject = new JSONObject();
            PrintWriter writer = response.getWriter();
            jsonObject.put("msg","token不存在,请先获取token");
            log.error("token不存在,请先获取token");
            writer.print(jsonObject);
            return false;
        }
        else {
            JSONObject verify = JwtUtils.verify(token);
            if ("true".equals(verify.get("msg"))){
                return true;
            }
            else {
                JSONObject jsonObject = new JSONObject();
                PrintWriter writer = response.getWriter();
                jsonObject.put("msg",verify.get("msg"));
                writer.print(jsonObject);
                log.error(String.valueOf(jsonObject));
                return false;
            }
        }
    }
}
