package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonPage;
import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.modules.ums.dto.UmsResourceParam;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import com.example.tiny_demo.modules.ums.service.UmsResourceService;
import com.example.tiny_demo.security.component.DynamicSecurityMetadataSource;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "UmsResourceController", description = "后台资源管理")
@RestController
@RequestMapping("/resource")
public class UmsResourceController {

    @Autowired
    private UmsResourceService resourceService;

    @Autowired(required = false)
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("根据id获取资源详情")
    @GetMapping("/{id}")
    public CommonResult listOne(@PathVariable Integer id) {
        UmsResourceDo resourceDo = resourceService.getById(id);
        return CommonResult.success(resourceDo);
    }

    @ApiOperation(value = "查询后台所有资源")
    @GetMapping("/listAll")
    public CommonResult listAll() {
        List<UmsResourceDo> list = resourceService.list(null);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "分页模糊查询后台资源")
    @PostMapping("/list")
    public CommonPage<UmsResourceDo> list(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                                          @RequestParam(value = "nameKeyword", required = false) String nameKeyword,
                                          @RequestParam(value = "urlKeyword", required = false) String urlKeyword,
                                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageInfo<UmsResourceDo> list = resourceService.list(categoryId, nameKeyword, urlKeyword, pageNum, pageSize);
        return CommonPage.restPage(list);
    }

    @ApiOperation(value = "添加后台资源")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsResourceParam resourceParam) {
        UmsResourceDo umsResourceDo = resourceService.create(resourceParam);
        clearSecuritySource();
        return CommonResult.success(umsResourceDo);
    }

    @ApiOperation(value = "根据id删除后台资源")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        resourceService.delete(id);
        clearSecuritySource();
        return CommonResult.success(null);
    }

    @ApiOperation(value = "修改后台资源")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Integer id,@RequestBody UmsResourceParam resourceParam) {
        UmsResourceDo update = resourceService.update(id, resourceParam);
        clearSecuritySource();
        return CommonResult.success(update);
    }

    /**
     * 清空安全元数据来源
     */
    private void clearSecuritySource() {
        if (dynamicSecurityMetadataSource != null) {
            dynamicSecurityMetadataSource.clear();
        }
    }
}
