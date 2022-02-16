package com.example.tiny_demo.mapper;

import com.example.tiny_demo.modules.ums.mapper.UmsResourceCategoryMapper;
import com.example.tiny_demo.modules.ums.model.UmsResourceCategoryDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UmsResourceCategoryMapperTest {

    @Autowired
    private UmsResourceCategoryMapper mapper;

    @Test
    public void listAllTest() {
        List<UmsResourceCategoryDo> list = mapper.selectList(new UmsResourceCategoryDo());
        list.stream().forEach(System.out::println);
    }
}
