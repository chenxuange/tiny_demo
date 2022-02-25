package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonPage;
import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.modules.ums.dto.UmsRoleParam;
import com.example.tiny_demo.modules.ums.model.*;
import com.example.tiny_demo.modules.ums.service.UmsRoleService;
import com.example.tiny_demo.modules.ums.service.impl.UmsAdminServiceImpl;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "UmsRoleController", description = "后台用户角色管理")
@RestController
@RequestMapping("/role")
public class UmsRoleController {

    private static final Logger logger = LoggerFactory.getLogger(UmsRoleController.class);

    @Autowired
    private UmsRoleService roleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/listAll")
    public CommonResult<List<UmsRoleDo>> listAll() {
        List<UmsRoleDo> list = roleService.listAll();
        logger.debug("UmsRoleController.listAll, {}", "");
        return CommonResult.success(list);
    }

    @ApiOperation("根据模糊角色名称分页获取角色列表")
    @PostMapping("/list")
    public CommonPage<Object> list(@RequestParam(required = false) String keyword,
                                   @RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize) {
        PageInfo<Object> pageInfo = roleService.list(keyword, pageNum, pageSize);
        return CommonPage.restPage(pageInfo);
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping("/listMenu/{roleId}")
    public CommonResult<List<UmsMenuDo>> listMenu(@PathVariable Integer roleId) {
        List<UmsMenuDo> list = roleService.listMenu(roleId);
        return CommonResult.success(list);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping("/listResource/{roleId}")
    public CommonResult<Object> listResource(@PathVariable Integer roleId) {
        List<UmsResourceDo> list = roleService.listResource(roleId);
        return CommonResult.success(list);
    }

    @ApiOperation("添加角色")
    @PostMapping("/create")
    public CommonResult<Object> create(@RequestBody UmsRoleParam roleParam) {
        //  实际不需要考虑这么多，比如增删改有专门的权限控制框架控制，业务逻辑只需要考虑执行功能
        roleService.create(roleParam);
        return CommonResult.success("添加角色完成");
    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public CommonResult<Object> update(@PathVariable Integer id, @RequestBody UmsRoleParam roleParam) {
        roleService.update(id, roleParam);
        return CommonResult.success("更新角色信息完成");
    }

    @ApiOperation("修改角色状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult<Object> updateStatus(@PathVariable Integer id, @RequestParam Integer status) {
        roleService.updateStatus(id, status);
        return CommonResult.success("修改角色状态完成");
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping("/allocMenu/{roleId}")
    public CommonResult<Object> allocMenu(@PathVariable Integer roleId, @RequestParam List<Integer> menuIds) {
        roleService.allocMenu(roleId, menuIds);
        return CommonResult.success(null);

    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/allocResource/{roleId}")
    public CommonResult<Object> allocResource(@PathVariable Integer roleId, @RequestParam List<Integer> resourceIds){
         roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(null);

    }

    @ApiOperation("批量删除角色")
    @PostMapping("/deleteBatch")
    public CommonResult<Object> deleteBatch(@RequestParam List<Integer> ids){
        roleService.deleteBatch(ids);
        return CommonResult.success(null);
    }
}
