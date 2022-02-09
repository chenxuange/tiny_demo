package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.modules.ums.UmsAdminParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Api(tags = "UmsAdminController", description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {


    @ApiOperation(value = "获取指定用户信息", notes = "新增注意事项")
    @GetMapping("/{id}")
    public CommonResult userInfo(@PathVariable String id) {
        return CommonResult.success(id);
    }

    @ApiOperation(value = "删除指定用户信息")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable String id) {
        return CommonResult.success(id);
    }

    @ApiOperation(value = "获取当前登陆用户信息")
    @GetMapping("/info")
    public CommonResult info(@RequestParam String name) {
        return CommonResult.success(name);
    }

    @ApiOperation(value = "根据用户名或姓名以及分页参数获取用户列表")
    @GetMapping("/list")
    public CommonResult list(@RequestParam String keyword,
                             @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        return CommonResult.success(keyword +" "+ pageNum +" "+ pageSize);
    }

    @ApiOperation(value = "登陆以后返回token")
    @PostMapping("/login")
    public CommonResult login(@RequestParam String username, @RequestParam String password) {
        return CommonResult.success(username + " " + password);
    }

    @ApiOperation(value = "登出功能")
    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success("logout");
    }

    @ApiOperation("刷新token")
    @GetMapping("/refreshToken")
    public CommonResult refresh() {
        return CommonResult.success("refresh");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CommonResult register(@RequestBody UmsAdminParam adminParam) {
        return CommonResult.success(adminParam);
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping("/role/{adminId}")
    public CommonResult roleInfo(@PathVariable String adminId) {
        return CommonResult.success(adminId);
    }

    @ApiOperation(value = "给用户分配角色")
    @PostMapping("/role/update")
    public CommonResult assignRole(@RequestParam Integer adminId,
                                 @RequestParam List<Integer> roleIds) {
        return CommonResult.success(adminId + " " + roleIds.toString());
    }

    @ApiOperation(value = "修改指定用户信息")
    @PostMapping("/update/{id}")
    public CommonResult updateRole(@PathVariable Integer id,
                                   @RequestBody  UmsAdminParam adminParam) {
        return CommonResult.success(id + " " + adminParam.toString());
    }

    @ApiOperation(value = "修改指定用户密码")
    @PostMapping("/updatePassword")
    public CommonResult updatePassword() {
        return CommonResult.success("updatePassword");
    }

    @ApiOperation(value = "修改指定用户状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Integer id,
                                     @RequestParam Integer status) {
        return CommonResult.success(id+ " " + status);
    }

}
