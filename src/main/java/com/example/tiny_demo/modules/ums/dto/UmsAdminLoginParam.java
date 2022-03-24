package com.example.tiny_demo.modules.ums.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 参数类
 * 前端传参对象：用户登录
 */
@Data
public class UmsAdminLoginParam {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}

