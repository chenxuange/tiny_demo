package com.example.tiny_demo.modules.ums.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value ="UmsAdminDO对象", description = "后台用户表")
public class UmsAdminDO implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private String username;

    private String password;

    private String icon;

    private String email;

    private String nickName;

    private String note;

    private Date createTime;

    private Date loginTime;
    @ApiModelProperty(value = "账户启用状态: 0->禁用; 1->启用")
    private Integer status;
}

