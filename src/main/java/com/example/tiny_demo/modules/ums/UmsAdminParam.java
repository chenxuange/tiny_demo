package com.example.tiny_demo.modules.ums;

import lombok.Data;

/**
 * 用户参数类
 */
@Data
public class UmsAdminParam {
    private String username;
    private String password;
    private String icon;
    private String email;
    private String nickName;
    private String note;
}
