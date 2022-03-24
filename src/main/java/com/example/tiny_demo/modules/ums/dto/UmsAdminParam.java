package com.example.tiny_demo.modules.ums.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 参数类
 * 前端传参对象：用户更新
 */
@ApiModel(value = "UmsAdminParam", description = "前端用户参数")
@Data
public class UmsAdminParam {
    private String icon;
    @Email(message = "请输入正确的邮箱地址")
    private String email;
    private String nickName;
    private String note;
}
