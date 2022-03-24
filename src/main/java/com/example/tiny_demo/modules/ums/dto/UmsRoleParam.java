package com.example.tiny_demo.modules.ums.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数类
 * 前端传参对象：角色更新
 */
@ApiModel(value = "UmsRoleParam对象", description = "前端角色传参")
@Data
public class UmsRoleParam {

    @ApiModelProperty("角色名称")
    private String name;

    private String description;


}
