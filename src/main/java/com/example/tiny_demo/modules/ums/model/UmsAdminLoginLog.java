package com.example.tiny_demo.modules.ums.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "UmsAdminLoginLog对象", description = "后台登录日志表")
public class UmsAdminLoginLog {
    private Integer id;
    private Integer adminId;
    @ApiModelProperty(value = "用户登录时间")
    private Date createTime;
    private String ip;
    private String address;
    private String userAgent;
}
