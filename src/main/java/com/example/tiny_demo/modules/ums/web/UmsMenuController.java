package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "UmsMenuController", description = "后台菜单管理")
@RestController
@RequestMapping("/")
public class UmsMenuController {

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

}
