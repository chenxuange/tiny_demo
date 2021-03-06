package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.exception.Asserts;
import com.example.tiny_demo.modules.ums.dto.UmsRoleParam;
import com.example.tiny_demo.modules.ums.mapper.*;
import com.example.tiny_demo.modules.ums.mapper.UmsRoleResourceRMapper;
import com.example.tiny_demo.modules.ums.model.*;
import com.example.tiny_demo.modules.ums.service.UmsRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsMenuMapper menuMapper;

    @Autowired
    private UmsResourceMapper resourceMapper;

    @Autowired
    private UmsRoleMenuRMapper roleMenuRMapper;

    @Autowired
    private UmsRoleResourceRMapper roleResourceRMapper;

    @Override
    public List<UmsRoleDo> listAll() {
        List<UmsRoleDo> list = roleMapper.selectList(new UmsRoleDo());
        return list;
    }

    @Override
    public PageInfo<Object> list(String keyword, Integer pageNum, Integer pageSize) {
        PageInfo<Object> pageInfo = PageHelper.startPage(pageNum, pageSize).doSelectPageInfo(() -> {
            UmsRoleDo query = new UmsRoleDo();
            if(!StringUtils.isEmpty(keyword)) {
                query.setName("%" + keyword + "%");
            }
            roleMapper.selectList(query);
        });
        return pageInfo;
    }

    @Override
    public List<UmsMenuDo> listMenu(Integer roleId) {
        List<UmsMenuDo> menuList = menuMapper.getMenuListByRoleId(roleId);
        return menuList;
    }

    @Override
    public List<UmsResourceDo> listResource(Integer roleId) {
        List<UmsResourceDo> resourceList = resourceMapper.getResourceListByRoleId(roleId);
        return resourceList;
    }

    @Override
    public UmsRoleDo create(UmsRoleParam roleParam) {
        // TODO ???????????????????????????name???????????????
        UmsRoleDo query = new UmsRoleDo();
        query.setName(roleParam.getName());
        List<UmsRoleDo> list = roleMapper.selectList(query);
        if(!CollectionUtils.isEmpty(list)) {
            Asserts.fail("?????????????????????");
        }
        UmsRoleDo roleDo = new UmsRoleDo();
        BeanUtils.copyProperties(roleParam, roleDo);
        roleDo.setCreateTime(new Date());
        // ????????????????????????
        roleDo.setStatus(1);
        // TODO ????????????????????????????????????sort?????????
        roleMapper.insert(roleDo);
        return roleDo;
    }

    @Override
    public UmsRoleDo update(Integer id, UmsRoleDo roleDo) {
        List<UmsRoleDo> list = roleMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("??????????????????");
        }
        roleDo.setId(id);
        roleMapper.updateById(roleDo);
        return roleDo;
    }

    @Override
    public UmsRoleDo updateStatus(Integer id, Integer status) {
        List<UmsRoleDo> list = roleMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("??????????????????");
        }
        UmsRoleDo roleDo = new UmsRoleDo();
        roleDo.setId(id);
        roleDo.setStatus(status);
        roleMapper.updateById(roleDo);
        return roleDo;
    }

    @Override
    public List<Integer> deleteBatch(List<Integer> ids) {
        roleMapper.deleteBatchIds(ids);
        return ids;
    }

    @Override
    public List<UmsRoleMenuR> allocMenu(Integer roleId, List<Integer> menuIds) {
        // ???????????????id??????????????????????????????id????????????
        List<UmsRoleDo> roleDoList = roleMapper.selectById(roleId);
        if(CollectionUtils.isEmpty(roleDoList)) {
            Asserts.fail("?????????????????????");
        }
        // TODO ?????????????????????????????????????????????????????????????????????
        List<UmsMenuDo> menuDoList = menuMapper.selectByIdBatch(menuIds);
        if(CollectionUtils.isEmpty(menuDoList) && menuDoList.size() == menuIds.size()) {
            Asserts.fail("????????????????????????");
        }
        // ????????????????????????????????????
        roleMenuRMapper.deleteByRoleId(roleId);
        List<UmsRoleMenuR> list = menuIds.stream().map(menuId -> {
            UmsRoleMenuR r = new UmsRoleMenuR();
            r.setRoleId(roleId);
            r.setMenuId(menuId);
            return r;
        }).collect(Collectors.toList());
        roleMenuRMapper.insertBatch(list);
        return list;
    }

    @Override
    public List<UmsRoleResourceR> allocResource(Integer roleId, List<Integer> resourceIds) {
        // ???????????????id??????????????????????????????id????????????
        List<UmsRoleDo> roleDoList = roleMapper.selectById(roleId);
        if(CollectionUtils.isEmpty(roleDoList)) {
            Asserts.fail("?????????????????????");
        }
        // TODO ?????????????????????????????????????????????????????????????????????
        List<UmsResourceDo> resourceDoList = resourceMapper.selectByIdBatch(resourceIds);
        if(CollectionUtils.isEmpty(resourceDoList) && resourceDoList.size() == resourceIds.size()) {
            Asserts.fail("????????????????????????");
        }
        // ????????????????????????????????????????????????
        roleResourceRMapper.deleteByRoleId(roleId);
        List<UmsRoleResourceR> list = resourceIds.stream().map(resourceId -> {
            UmsRoleResourceR r = new UmsRoleResourceR();
            r.setRoleId(roleId);
            r.setResourceId(resourceId);
            return r;
        }).collect(Collectors.toList());
        roleResourceRMapper.insertBatch(list);
        return list;
    }
}
