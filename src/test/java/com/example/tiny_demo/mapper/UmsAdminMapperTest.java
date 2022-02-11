package com.example.tiny_demo.mapper;

import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.mapper.UmsAdminMapper;
import com.example.tiny_demo.modules.ums.query.UmsAdminQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UmsAdminMapperTest {

    @Autowired
    public UmsAdminMapper mapper;

    @Test
    public void selectListTest() {
        List<UmsAdminDO> umsAdminDOS = mapper.selectList(new UmsAdminDO());
        System.out.println(umsAdminDOS);
    }

    @Test
    public void insertTest() {
        UmsAdminDO umsAdminDO = new UmsAdminDO();
        umsAdminDO.setUsername("insert test");
        mapper.insert(umsAdminDO);
    }

    @Test
    public void selectByIdBatchTest() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        List<UmsAdminDO> adminDOList = mapper.selectByIdBatch(list);
        System.out.println(adminDOList);
    }
}
