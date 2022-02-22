package com.example.tiny_demo.mapper;

import com.example.tiny_demo.modules.ums.mapper.UmsAdminLoginLogMapper;
import com.example.tiny_demo.modules.ums.model.UmsAdminLoginLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
public class UmsAdminLoginLogMapperTest {

    @Autowired
    private UmsAdminLoginLogMapper mapper;

//    @Transactional
    @Test
    public void insertTest() {
        UmsAdminLoginLog log = new UmsAdminLoginLog();
        log.setAdminId(111);
        log.setCreateTime(new Date());
        log.setAddress("test addr");
        log.setIp("168.0.0.1");
        mapper.insert(log);
    }
}
