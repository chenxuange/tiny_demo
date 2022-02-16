package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.modules.ums.dto.UmsResourceCategoryParam;
import com.example.tiny_demo.modules.ums.model.UmsResourceCategoryDo;
import com.example.tiny_demo.modules.ums.service.UmsResourceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "UmsResourceCategoryController", description = "后台资源分类管理")
@RestController
@RequestMapping("/resourceCategory")
public class UmsResourceCategoryController {

    @Autowired
    private UmsResourceCategoryService resourceCategoryService;

    @ApiOperation("查询所有后台资源分类")
    @GetMapping("/listAll")
    public CommonResult listAll() {
        List<UmsResourceCategoryDo> list = resourceCategoryService.listAll();
        return CommonResult.success(list);
    }

    @ApiOperation("添加后台资源分类")
    @PostMapping("/create")
    public CommonResult<UmsResourceCategoryDo> create(@RequestBody UmsResourceCategoryParam resourceCategoryParam) {
        UmsResourceCategoryDo umsResourceCategoryDo = resourceCategoryService.create(resourceCategoryParam);
        return CommonResult.success(umsResourceCategoryDo);

    }

    @ApiOperation("修改后台资源分类")
    @PostMapping("/update/{id}")
    public CommonResult<UmsResourceCategoryDo> update(@PathVariable Integer id, @RequestBody UmsResourceCategoryParam resourceCategoryParam) {
        UmsResourceCategoryDo update = resourceCategoryService.update(id, resourceCategoryParam);
        return CommonResult.success(update);

    }

    @ApiOperation("删除后台资源分类")
    @PostMapping("/delete/{id}")
    public CommonResult<Object> delete(@PathVariable Integer id) {
        resourceCategoryService.delete(id);
        return CommonResult.success(null);

    }
}
