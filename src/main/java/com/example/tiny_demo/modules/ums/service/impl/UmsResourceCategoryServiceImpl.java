package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.common.exception.Asserts;
import com.example.tiny_demo.modules.ums.dto.UmsResourceCategoryParam;
import com.example.tiny_demo.modules.ums.mapper.UmsResourceCategoryMapper;
import com.example.tiny_demo.modules.ums.model.UmsResourceCategoryDo;
import com.example.tiny_demo.modules.ums.service.UmsResourceCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {

    @Autowired
    private UmsResourceCategoryMapper resourceCategoryMapper;
    @Override
    public List<UmsResourceCategoryDo> listAll() {
        List<UmsResourceCategoryDo> list = resourceCategoryMapper.selectList(new UmsResourceCategoryDo());
        return list;
    }

    @Override
    public UmsResourceCategoryDo create(UmsResourceCategoryParam resourceCategoryParam) {
        UmsResourceCategoryDo query = new UmsResourceCategoryDo();
        query.setName(resourceCategoryParam.getName());
        List<UmsResourceCategoryDo> list = resourceCategoryMapper.selectList(query);
        if(!CollectionUtils.isEmpty(list)) {
            Asserts.fail("该资源分类名已存在");
        }
        UmsResourceCategoryDo umsResourceCategoryDo = new UmsResourceCategoryDo();
        BeanUtils.copyProperties(resourceCategoryParam, umsResourceCategoryDo);
        umsResourceCategoryDo.setCreateTime(new Date());
        resourceCategoryMapper.insert(umsResourceCategoryDo);
        return umsResourceCategoryDo;
    }

    @Override
    public UmsResourceCategoryDo update(Integer id, UmsResourceCategoryParam resourceCategoryParam) {
        List<UmsResourceCategoryDo> list = resourceCategoryMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("该资源分类不存在");
        }
        UmsResourceCategoryDo resourceCategoryDo = new UmsResourceCategoryDo();
        BeanUtils.copyProperties(resourceCategoryParam, resourceCategoryDo);
        resourceCategoryDo.setId(id);
        resourceCategoryMapper.updateById(resourceCategoryDo);
        return resourceCategoryDo;
    }

    @Override
    public void delete(Integer id) {
        List<UmsResourceCategoryDo> list = resourceCategoryMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("该资源分类不存在");
        }
        resourceCategoryMapper.deleteById(id);
    }
}
