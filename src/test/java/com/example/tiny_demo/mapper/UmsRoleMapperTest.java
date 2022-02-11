package com.example.tiny_demo.mapper;

import com.example.tiny_demo.modules.ums.mapper.UmsRoleMapper;
import com.example.tiny_demo.modules.ums.model.UmsRoleDo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UmsRoleMapperTest {

    @Autowired
    private UmsRoleMapper mapper;
    @Test
    public void getRoleListTest() {
        Integer id = 999;
        List<UmsRoleDo> roleList = mapper.getRoleList(id);
        System.out.println(roleList);
        Assert.assertNotNull(roleList);
        System.out.println(roleList == null);
        System.out.println(roleList.size());


    }
}
