package com.saofeng.admin.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

import static com.saofeng.admin.utils.JwtUtils.verify;
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null) {
            DecodedJWT verify = verify(token);
            String token1 = verify.getToken();
            if (verify.getToken() == null || verify.getToken().equals("")) {
                return false;
            }
            return true;
        }
        PrintWriter responseWriter = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "invalid token");
        responseWriter.print(jsonObject);
        return false;
    }
}
