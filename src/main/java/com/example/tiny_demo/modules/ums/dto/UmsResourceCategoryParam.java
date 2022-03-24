package com.example.tiny_demo.modules.ums.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 参数类
 * 前端传参对象：资源分类更新
 *
 */
@ApiModel(value = "UmsResourceCategoryParam对象", description = "前端资源分类传参")
@Data
public class UmsResourceCategoryParam {

    @ApiModelProperty(value = "资源分类名")
    private String name;
    @ApiModelProperty("资源排序")
    private Integer sort;
}
