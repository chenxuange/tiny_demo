package com.example.tiny_demo.modules.ums.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "UmsResourceCategoryDo对象", description = "后台资源分类表")
@Data
public class UmsResourceCategoryDo {
    private Integer id;
    private Date createTime;
    @ApiModelProperty(value = "资源分类名")
    private String name;
    @ApiModelProperty("资源排序")
    private Integer sort;
}
