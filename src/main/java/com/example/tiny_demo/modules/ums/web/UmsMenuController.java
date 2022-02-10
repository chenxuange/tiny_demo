package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.modules.ums.dto.UmsMenuNode;
import com.example.tiny_demo.modules.ums.model.UmsMenuDo;
import com.example.tiny_demo.modules.ums.service.UmsMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "UmsMenuController", description = "后台菜单管理")
@RestController
@RequestMapping("/menu")
public class UmsMenuController {

    private static final Logger logger = LoggerFactory.getLogger(UmsMenuController.class);

    @Autowired
    private UmsMenuService menuService;

    @ApiOperation(value = "根据ID获取菜单详情")
    @GetMapping("/{id}")
    public CommonResult menu(@PathVariable Integer id) {
        UmsMenuDo umsMenuDo = menuService.selectById(id);
        return CommonResult.success(umsMenuDo);
    }

    @ApiOperation(value = "添加后台菜单")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsMenuDo umsMenuDo) {
        try {
            menuService.create(umsMenuDo);
        } catch (Exception e) {
            logger.info("UmsMenuController.create, {}", e.getMessage());
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "根据ID删除后台菜单")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        try {
            menuService.delete(id);
        } catch (Exception e) {
            logger.info("UmsMenuController.delete, {}", e.getMessage());
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "分页查询后台菜单")
    @PostMapping("/list/{parentId}")
    public CommonResult list(@PathVariable("parentId") Integer parentId,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        List<UmsMenuDo> menuDoList = menuService.selectPage(parentId, pageNum, pageSize);
        return CommonResult.success(menuDoList);
    }

    @ApiOperation(value = "树形结构返回所有的菜单列表")
    @GetMapping("/treeList")
    public CommonResult treeList() {
        List<UmsMenuNode> umsMenuNodes = menuService.treeList();
        return CommonResult.success(umsMenuNodes);
    }

    @ApiOperation(value = "修改后台菜单")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Integer id, @RequestBody UmsMenuDo umsMenuDo) {
        try {
            menuService.update(id, umsMenuDo);
        } catch (Exception e) {
            logger.info("UmsMenuController.update, {}", e.getMessage());
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "修改菜单显示状态")
    @PostMapping("/updateHidden/{id}")
    public CommonResult updateHidden(@PathVariable Integer id, @RequestParam Integer hidden) {
        try {
            menuService.updateHidden(id, hidden);
        } catch (Exception e) {
            logger.info("UmsMenuController.updateHidden, {}", e.getMessage());
            return CommonResult.fail(null);
        }
        return CommonResult.success(null);
    }

}
