package com.example.tiny_demo.mapper;

import com.example.tiny_demo.model.UmsAdmin;
import com.example.tiny_demo.query.UmsAdminQuery;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
