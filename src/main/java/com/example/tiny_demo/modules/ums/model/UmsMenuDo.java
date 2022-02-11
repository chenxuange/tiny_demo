package com.example.tiny_demo.modules.ums.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "UmsMenuDo对象", description = "后台菜单表")
@Data
public class UmsMenuDo {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "父类id")
    private Integer parentId;
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    @ApiModelProperty(value = "菜单名称")
    private String title;
    @ApiModelProperty(value = "菜单级数")
    private Integer level;
    @ApiModelProperty(value = "菜单排序")
    private Integer sort;
    @ApiModelProperty(value = "前端名称")
    private String name;
    @ApiModelProperty("前端图标")
    private String icon;
    @ApiModelProperty("前端隐藏")
    private Integer hidden;


}
