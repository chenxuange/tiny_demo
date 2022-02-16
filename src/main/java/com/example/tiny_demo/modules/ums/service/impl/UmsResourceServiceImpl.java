package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.exception.Asserts;
import com.example.tiny_demo.modules.ums.dto.UmsResourceParam;
import com.example.tiny_demo.modules.ums.mapper.UmsResourceMapper;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import com.example.tiny_demo.modules.ums.service.UmsResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class UmsResourceServiceImpl implements UmsResourceService {

    @Autowired
    private UmsResourceMapper resourceMapper;
    @Override
    public List<UmsResourceDo> list(UmsResourceDo resourceDo) {
        List<UmsResourceDo> list = resourceMapper.selectList(resourceDo);
        return list;
    }

    @Override
    public UmsResourceDo getById(Integer id) {
        List<UmsResourceDo> list = resourceMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public PageInfo<UmsResourceDo> list(Integer categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize) {
        PageInfo<UmsResourceDo> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            UmsResourceDo resourceDo = new UmsResourceDo();
            resourceDo.setCategoryId(categoryId);
            if(!StringUtils.isEmpty(nameKeyword)) {
                resourceDo.setName("%" + nameKeyword + "%");
            }
            if(!StringUtils.isEmpty(urlKeyword)) {
                resourceDo.setUrl("%" + urlKeyword + "%");
            }
            resourceMapper.selectList(resourceDo);
        });
        return pageInfo;
    };

    @Override
    public UmsResourceDo create(UmsResourceParam resourceParam) {
        // 实际插入时，name和url应该时唯一约束，因此库中暂时添加了name唯一约束
        UmsResourceDo resourceDo = new UmsResourceDo();
        BeanUtils.copyProperties(resourceParam, resourceDo);
        resourceDo.setCreateTime(new Date());
        resourceMapper.insert(resourceDo);
        return resourceDo;
    }

    @Override
    public void delete(Integer id) {
        List<UmsResourceDo> list = resourceMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("该用户不存在");
        }
        resourceMapper.deleteById(id);
    }

    @Override
    public UmsResourceDo update(Integer id, UmsResourceParam resourceParam) {
        // 更新前先查询
        List<UmsResourceDo> list = resourceMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("该用户不存在");
        }
        UmsResourceDo umsResourceDo = new UmsResourceDo();
        BeanUtils.copyProperties(resourceParam, umsResourceDo);
        umsResourceDo.setId(id);
        resourceMapper.updateById(umsResourceDo);
        return umsResourceDo;
    }
}
