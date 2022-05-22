package com.saofeng.admin.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class DigestPassword {
    public static String getMd5Password(String salt, String password) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes(StandardCharsets.UTF_8));
        }
        return password;
    }
}
