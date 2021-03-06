package com.example.tiny_demo.modules.ums.web;

import com.example.tiny_demo.common.api.CommonPage;
import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.common.api.ResultCode;
import com.example.tiny_demo.modules.ums.dto.UmsAdminLoginParam;
import com.example.tiny_demo.modules.ums.dto.UmsAdminParam;
import com.example.tiny_demo.modules.ums.dto.UpdateAdminPasswordParam;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsRoleDo;
import com.example.tiny_demo.modules.ums.service.UmsAdminService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Api(tags = "UmsAdminController", description = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminController.class);
    @Autowired
    private UmsAdminService adminService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @ApiOperation(value = "获取指定用户信息", notes = "新增注意事项")
    @GetMapping("/{id}")
    public CommonResult userInfo(@PathVariable Integer id) {
        UmsAdminDO umsAdminDO = adminService.get(id);

        // TODO 模拟一个极简用户登录, 待安全功能完备
        UserDetails userDetails = new User("tom", "", Collections.singleton(() -> null));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return CommonResult.success(umsAdminDO);
    }

    @ApiOperation(value = "删除指定用户信息")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Integer id) {
        adminService.delete(id);
        return CommonResult.success(null);
    }

    /**
     * 获取当前认证用户(authenticated principal)
     * 1。 最简单的方式是通过Bean获取用户，即通过SecurityContextHolder类的静态方法,如
     * Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     * 2. 在控制器中获取用户，可以选择 Principal 或 Authentication 直接作为方法参数
     * 这种接口调试时，会显示默认参数，实际可不输入
     * 3. 直接通过http request, 即通过 Principal principal = request.getUserPrincipal();
     * 小结：可选择第1种或第3种方式获取当前认证用户
     */
    @ApiOperation(value = "获取当前登陆用户信息")
    @GetMapping("/info")
    public CommonResult info(HttpServletRequest request) {
        // TODO 待安全功能完备
        Principal principal = request.getUserPrincipal();
        if (principal == null) {
            System.out.println("Principal is null");
            return CommonResult.fail(null);
        }
        String name = principal.getName();
        return CommonResult.success(name);
    }

    @ApiOperation(value = "根据用户名或姓名以及分页参数获取用户列表")
    @GetMapping("/list")
    public CommonResult list(@RequestParam(value = "keyword", required = false) String keyword,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        PageInfo<UmsAdminDO> pageInfo = adminService.list(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pageInfo));
    }

    @ApiOperation(value = "登陆以后返回token")
    @PostMapping("/login")
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam adminLoginParam) {
        String token = adminService.login(adminLoginParam);
        if (StringUtils.isEmpty(token)) {
            // 用户名或密码错误，不能给出直接提示密码不正确
            return CommonResult.fail(ResultCode.USERNAME_OR_PASSWORD_ERROR, null);
        }
        HashMap<String, String> map = new HashMap<>();
        map.put(tokenHead, "Bearer "); // 注意Bearer 后有个空格
        map.put("token", token);
        return CommonResult.success(map);
    }

    @ApiOperation(value = "登出功能")
    @PostMapping("/logout")
    public CommonResult logout() {
        // 注销当前用户
        return CommonResult.success("logout");
    }

    @ApiOperation("刷新token")
    @GetMapping("/refreshToken")
    public CommonResult refresh() {
        // TODO 待安全功能完备
        return CommonResult.success("refresh");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public CommonResult register(@Validated @RequestBody UmsAdminParam adminParam) {
        UmsAdminDO register = adminService.register(adminParam);
        return CommonResult.success(register);
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping("/role/{adminId}")
    public CommonResult roleInfo(@PathVariable Integer adminId) {
        List<UmsRoleDo> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

    @ApiOperation(value = "给用户分配角色")
    @PostMapping("/role/update")
    public CommonResult assignRole(@RequestParam Integer adminId,
                                   @RequestParam List<Integer> roleIds) {
        adminService.updateRole(adminId, roleIds);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "修改指定用户信息")
    @PostMapping("/update/{id}")
    public CommonResult updateUser(@PathVariable Integer id,
                                   @Validated @RequestBody UmsAdminParam adminParam) {
        UmsAdminDO umsAdminDO = adminService.updateUser(id, adminParam);
        return CommonResult.success(umsAdminDO);
    }

    @ApiOperation(value = "修改指定用户密码")
    @PostMapping("/updatePassword")
    public CommonResult updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updateAdminPasswordParam) {
        adminService.updatePassword(updateAdminPasswordParam);
        return CommonResult.success(null);
    }

    @ApiOperation(value = "修改指定用户状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Integer id,
                                     @RequestParam Integer status) {
        UmsAdminParam adminParam = new UmsAdminParam();
        adminParam.setStatus(status);
        adminService.updateUser(id, adminParam);
        return CommonResult.success(null);
    }

}
