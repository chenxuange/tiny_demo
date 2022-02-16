package com.example.tiny_demo.modules.ums.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "UmsResourceParam", description = "前端资源参数")
public class UmsResourceParam implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "资源名称")
    private String name;
    @ApiModelProperty(value = "资源url，决定访问路径")
    private String url;
    private String description;
    @ApiModelProperty(value = "资源分类id")
    private Integer categoryId;


}
