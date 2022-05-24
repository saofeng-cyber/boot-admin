package com.saofeng.admin.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.saofeng.admin.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Value;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    private static final String secret = "saofeng@lovedongjie";
    private static final String issue = "saofeng";
    private static final String expireDate = "1314521sun";

    // userInfo 生成token
    public static String getToken(UserInfo userInfo) {
        Date now = new Date();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        Algorithm hmac256 = Algorithm.HMAC256(secret);
        String token = JWT.create().withIssuer(issue).withIssuedAt(now).withExpiresAt(new Date(now.getTime() + expireDate)).withClaim("username", userInfo.getUsername()).withClaim("userid", userInfo.getUid()).sign(hmac256);
        return token;
    }

    // map 生成token
    public static String createToken(Map<String, String> map) {
        //创建过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);  //7天过期

        //创建builder对象
        JWTCreator.Builder builder = JWT.create();
        //遍历map
        map.forEach(builder::withClaim);
        String token = builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(secret));
        return token;
    }

    // 验证token
    public static JSONObject verifyToken(String token, String username) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JSONObject jsonObject = new JSONObject();
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim("username", username).build();
            jwtVerifier.verify(token);
            return jsonObject;
        } catch (SignatureVerificationException e) {
            //验证的签名不一致
            throw new SignatureVerificationException(algorithm);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("token is alreadey expired");
        } catch (AlgorithmMismatchException e) {
            throw new AlgorithmMismatchException("签名算法不匹配");
        } catch (InvalidClaimException e) {
            throw new InvalidClaimException("校验的claims内容不匹配");
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.put("result", "用户和jwt-token校验失败");
        return jsonObject;
    }

    // 验证token
    public static DecodedJWT verify(String token) {
            return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }
}
