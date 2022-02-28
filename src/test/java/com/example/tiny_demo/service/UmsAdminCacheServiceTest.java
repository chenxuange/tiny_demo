package com.example.tiny_demo.service;

import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import com.example.tiny_demo.modules.ums.service.UmsAdminCacheService;
import com.example.tiny_demo.modules.ums.service.UmsAdminService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UmsAdminCacheServiceTest {

    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Test
    @Order(0)
    public void setAdminTest() {
        UmsAdminDO admin = adminService.getAdminByUsername("valerius");
        adminCacheService.setAdmin(admin);
    }

    @Test
    @Order(1)
    public void getAdminTest() {
        UmsAdminDO valerius = adminCacheService.getAdmin("valerius");
        System.out.println(valerius);
    }

    @Test
    @Order(2)
    public void delAdminTest() {
        adminCacheService.delAdmin(22);
    }

    @Test
    @Order(21)
    public void setResourceListTest() {
        List<UmsResourceDo> resourceList = adminService.getResourceList(3);
        adminCacheService.setResourceList(3, resourceList);
    }

    @Test
    @Order(22)
    public void getResourceListTest() {
        List<UmsResourceDo> resourceList = adminCacheService.getResourceList(3);
        resourceList.stream().forEach(System.out::println);
    }

    @Test
    @Order(231)
    public void delResourceListTest() {
        adminCacheService.delResourceList(3);
    }

    @Test
    @Order(232)
    public void delResourceListByRoleTest() {
        adminCacheService.delResourceListByRole(5);
    }

    @Test
    @Order(233)
    public void delResourceListByResourceTest() {
        adminCacheService.delResourceListByResource(25);
    }
}
