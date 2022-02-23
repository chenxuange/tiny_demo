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

    private Integer id;

    private String username;

    private String password;
    @ApiModelProperty(value = "头像")
    private String icon;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "备注信息")
    private String note;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty(value = "最后登录时间")
    private Date loginTime;
    @ApiModelProperty(value = "账户启用状态: 0->禁用; 1->启用")
    private Integer status;
    @ApiModelProperty(value = "判断token失效的操作时间")
    private Date operatorTime;
}

