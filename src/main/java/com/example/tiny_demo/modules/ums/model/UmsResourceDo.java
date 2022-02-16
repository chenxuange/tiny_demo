package com.example.tiny_demo.modules.ums.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "UmsResourceDo", description = "后台资源表")
public class UmsResourceDo implements Serializable {
    private static final long serialVersionUID=1L;
    private Integer id;
    private Date createTime;
    @ApiModelProperty(value = "资源名称")
    private String name;
    @ApiModelProperty(value = "资源url，决定访问路径")
    private String url;
    private String description;
    @ApiModelProperty(value = "资源分类id")
    private Integer categoryId;


}
