package com.example.tiny_demo.modules.ums.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "UmsRoleResourceR", description = "角色资源关联表")
@Data
public class UmsRoleResourceR {
    private Integer id;
    private Integer roleId;
    private Integer resourceId;
}
