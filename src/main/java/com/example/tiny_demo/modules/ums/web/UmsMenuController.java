package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "UmsMenuController", description = "后台菜单管理")
@RestController
@RequestMapping("/menu")
public class UmsMenuController {

    @ApiOperation(value = "根据ID获取菜单详情")
    @GetMapping("/{id}")
    public CommonResult menu(@PathVariable Integer id) {
        return CommonResult.success(id);
    }

    @ApiOperation(value = "添加后台菜单")
    @PostMapping("/create")
    public CommonResult create() {
        return CommonResult.success("create");
    }

    @ApiOperation(value = "根据ID删除后台菜单")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        return CommonResult.success(id);
    }

    @ApiOperation(value = "分页查询后台菜单")
    @PostMapping("/list/{parentId}")
    public CommonResult list(@PathVariable("parentId") Integer parentId,
                               @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return CommonResult.success(parentId + " " + pageNum + " "+ pageSize);
    }

    @ApiOperation(value = "树形结构返回所有的菜单列表")
    @GetMapping("/treeList")
    public CommonResult treeList() {
        return CommonResult.success("treeList");
    }

    @ApiOperation(value = "修改后台菜单")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Integer id) {
        return CommonResult.success(id);
    }

    @ApiOperation(value = "修改菜单显示状态")
    @PostMapping("/updateHidden/{id}")
    public CommonResult updateHidden(@PathVariable Integer id) {
        return CommonResult.success(id);
    }

}
