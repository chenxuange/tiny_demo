package com.example.tiny_demo.mapper;

import com.example.tiny_demo.modules.ums.mapper.UmsMenuMapper;
import com.example.tiny_demo.modules.ums.model.UmsMenuDo;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UmsMenuMapperTest {
    @Autowired
    UmsMenuMapper mapper;

    @Test
    public void selectByIdTest() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        List<UmsMenuDo> umsMenuDos = mapper.selectByIdBatch(ids);
        System.out.println(umsMenuDos);
    }

    @Test
    public void selectBatchTest() {
        UmsMenuDo umsMenuDo = new UmsMenuDo();
        umsMenuDo.setParentId(0);
        List<UmsMenuDo> list = mapper.selectBatch(umsMenuDo);
        System.out.println(list);
        list.stream().forEach(System.out::println);
    }

    @Test
    public void insertTest() {
        ArrayList<UmsMenuDo> list = new ArrayList<>();
        UmsMenuDo umsMenuDo = new UmsMenuDo();
        umsMenuDo.setParentId(23333);
        umsMenuDo.setTitle("测试新增菜单");
        list.add(umsMenuDo);
        mapper.insert(list);
    }

    @Test
    public void deleteTest() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(29);
        mapper.deleteByIdBatch(list);
    }

    @Test
    public void updateTest() {
        UmsMenuDo menuDo = new UmsMenuDo();
        menuDo.setId(44);
        menuDo.setParentId(789);
        mapper.update(menuDo);
    }
}
