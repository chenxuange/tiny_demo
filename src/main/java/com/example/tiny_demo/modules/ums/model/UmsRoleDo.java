package com.example.tiny_demo.modules.ums.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "UmsRoleDo", description = "后台角色表")
@Data
public class UmsRoleDo implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    @ApiModelProperty("角色名称")
    private String name;

    private String description;

    @ApiModelProperty("后台用户数量")
    private Integer adminCount;

    private Date createTime;
    @ApiModelProperty("启用状态：0->禁用；1->启用")
    private Integer status;

    private Integer sort;


}

