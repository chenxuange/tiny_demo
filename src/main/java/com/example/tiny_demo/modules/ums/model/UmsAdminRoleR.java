package com.example.tiny_demo.modules.ums.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "UmsAdminRoleR对象", description = "用户角色关系表")
@Data
public class UmsAdminRoleR {
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer adminId;
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}
