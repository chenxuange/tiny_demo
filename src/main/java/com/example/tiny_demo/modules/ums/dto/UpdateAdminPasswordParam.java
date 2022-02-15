package com.example.tiny_demo.modules.ums.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "UpdateAdminPasswordParam对象", description = "前端更新密码参数")
@Data
public class UpdateAdminPasswordParam {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "新密码不能为空")
    private String oldPassword;
    @NotBlank(message = "旧密码不能为空")
    private String newPassword;
}