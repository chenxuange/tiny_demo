package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "UmsAdminController", description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {


    @ApiOperation(value = "获取指定用户信息", notes = "新增注意事项")
    @GetMapping("/{id}")
    public CommonResult login(@PathVariable String id) {
        return CommonResult.success(id);
    }
}
