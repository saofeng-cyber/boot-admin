package com.saofeng.admin.utils;

import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

public class JwtUtils {
    public static final String key = "saofeng666";

    public static String createToken(Integer uid, String username) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 60 * 60 * 24);
        return JWT.create().withClaim("uuid", uid)
                .withClaim("username", username)
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(key));
    }

    public static JSONObject verify(String token) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        JSONObject jsonObject = new JSONObject();
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT verify = jwtVerifier.verify(token);
            System.out.println(verify);
            jsonObject.put("msg", "true");
            return jsonObject;
        } catch (SignatureVerificationException e) {
            //验证的签名不一致
            jsonObject.put("msg", "token签名不一致");
            return jsonObject;
        } catch (TokenExpiredException e) {
            jsonObject.put("msg", "token已过期");
            return jsonObject;
        } catch (AlgorithmMismatchException e) {
            jsonObject.put("msg", "签名算法不匹配");
            return jsonObject;
        } catch (InvalidClaimException e) {
            jsonObject.put("msg", "失效的payload异常");
            return jsonObject;
        } catch (Exception e) {
            jsonObject.put("success", "failed");
            return jsonObject;
        }
    }
}
