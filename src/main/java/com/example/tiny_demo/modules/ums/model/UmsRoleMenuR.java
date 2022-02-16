package com.example.tiny_demo.modules.ums.model;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "UmsRoleMenuR对象", description = "后台角色菜单关联表")
@Data
public class UmsRoleMenuR {
    private Integer id;
    private Integer roleId;
    private Integer menuId;
}
