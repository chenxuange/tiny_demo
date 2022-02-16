package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonPage;
import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.modules.ums.dto.UmsRoleParam;
import com.example.tiny_demo.modules.ums.model.*;
import com.example.tiny_demo.modules.ums.service.UmsRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "UmsRoleController", description = "后台用户角色管理")
@RestController
@RequestMapping("/role")
public class UmsRoleController {


    @Autowired
    private UmsRoleService roleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/listAll")
    public CommonResult<List<UmsRoleDo>> listAll() {
        List<UmsRoleDo> list = roleService.listAll();
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
        // TODO 实际不需要考虑这么多，比如增删改都应该是由具有特殊权限的人来操作，那么简单起见可以给最大权限
        UmsRoleDo roleDo = roleService.create(roleParam);
        return CommonResult.success(roleDo);

    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public CommonResult<Object> update(@PathVariable Integer id, @RequestBody UmsRoleDo roleDo) {
        UmsRoleDo update = roleService.update(id, roleDo);
        return CommonResult.success(update);
    }

    @ApiOperation("修改角色状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult<Object> updateStatus(@PathVariable Integer id, @RequestParam Integer status) {
        UmsRoleDo roleDo = roleService.updateStatus(id, status);
        return CommonResult.success(roleDo);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping("/allocMenu/{roleId}")
    public CommonResult<Object> allocMenu(@PathVariable Integer roleId, @RequestParam List<Integer> menuIds) {
        List<UmsRoleMenuR> umsRoleMenuRS = roleService.allocMenu(roleId, menuIds);
        return CommonResult.success(umsRoleMenuRS);

    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/allocResource/{roleId}")
    public CommonResult<Object> allocResource(@PathVariable Integer roleId, @RequestParam List<Integer> resourceIds){
        List<UmsRoleResourceR> list = roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(list);

    }

    @ApiOperation("批量删除角色")
    @PostMapping("/deleteBatch")
    public CommonResult<Object> deleteBatch(@RequestParam List<Integer> ids){
        List<Integer> list = roleService.deleteBatch(ids);
        return CommonResult.success(ids);
    }
}
