package com.example.tiny_demo.mapper;

import com.example.tiny_demo.modules.ums.model.UmsAdmin;
import com.example.tiny_demo.modules.ums.mapper.UmsAdminMapper;
import com.example.tiny_demo.modules.ums.query.UmsAdminQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UmsAdminMapperTest {

    @Autowired
    public UmsAdminMapper mapper;

    @Test
    public void test() {
        List<UmsAdmin> umsAdmins = mapper.selectList(new UmsAdminQuery());
        System.out.println(umsAdmins);
    }
}
