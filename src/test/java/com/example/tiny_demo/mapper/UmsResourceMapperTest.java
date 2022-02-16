package com.example.tiny_demo.mapper;

import com.example.tiny_demo.modules.ums.mapper.UmsResourceMapper;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class UmsResourceMapperTest {

    @Autowired
    private UmsResourceMapper mapper;

    @Test
    public void updateTest() {
        UmsResourceDo umsResourceDo = new UmsResourceDo();
        umsResourceDo.setId(41);
        umsResourceDo.setName("update test");
        mapper.updateById(umsResourceDo);
    }

    @Test
    public void insertTest() {
        UmsResourceDo umsResourceDo = new UmsResourceDo();
        umsResourceDo.setName("测试新增");
        mapper.insert(umsResourceDo);
    }

    @Test
    public void listAllTest() {
        List<UmsResourceDo> list = mapper.selectList(null);
        System.out.println(list);
    }

    @Test
    public void selectByIdTest() {
        List<UmsResourceDo> umsResourceDos = mapper.selectById(19);
        System.out.println(umsResourceDos);
    }
}
