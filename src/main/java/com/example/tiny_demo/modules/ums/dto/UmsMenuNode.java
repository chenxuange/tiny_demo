package com.example.tiny_demo.modules.ums.dto;

import com.example.tiny_demo.modules.ums.model.UmsMenuDo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 展示类
 * 前端展示对象：树形菜单结点，每个父级结点下存在子结点列表
 */
@Data
public class UmsMenuNode extends UmsMenuDo {
    @ApiModelProperty("子级菜单")
    List<UmsMenuNode> children;
}
