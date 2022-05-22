package com.saofeng.admin.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UserInfo {
    private Integer uid;
    private String username;
    private String password;
    private String token;
    private String salt;
    private Date createTime;
}
