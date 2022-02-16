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
        // TODO 实际角色表中应该给name加唯一约束
        UmsRoleDo query = new UmsRoleDo();
        query.setName(roleParam.getName());
        List<UmsRoleDo> list = roleMapper.selectList(query);
        if(!CollectionUtils.isEmpty(list)) {
            Asserts.fail("该角色已经存在");
        }
        UmsRoleDo roleDo = new UmsRoleDo();
        BeanUtils.copyProperties(roleParam, roleDo);
        roleDo.setCreateTime(new Date());
        // 新增角色默认启用
        roleDo.setStatus(1);
        // TODO 角色表中，后台用户数量和sort是什么
        roleMapper.insert(roleDo);
        return roleDo;
    }

    @Override
    public UmsRoleDo update(Integer id, UmsRoleDo roleDo) {
        List<UmsRoleDo> list = roleMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("该角色不存在");
        }
        roleDo.setId(id);
        roleMapper.updateById(roleDo);
        return roleDo;
    }

    @Override
    public UmsRoleDo updateStatus(Integer id, Integer status) {
        List<UmsRoleDo> list = roleMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("该角色不存在");
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
        // 先判断角色id是否存在，再判断菜单id是否存在
        List<UmsRoleDo> roleDoList = roleMapper.selectById(roleId);
        if(CollectionUtils.isEmpty(roleDoList)) {
            Asserts.fail("关联角色不存在");
        }
        // TODO 这步存在问题，应该每个建立关系的资源狗应该存在
        List<UmsMenuDo> menuDoList = menuMapper.selectByIdBatch(menuIds);
        if(CollectionUtils.isEmpty(menuDoList) && menuDoList.size() == menuIds.size()) {
            Asserts.fail("关联菜单有不存在");
        }
        // 删除指定角色下的菜单关系
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
        // 先判断角色id是否存在，再判断资源id是否存在
        List<UmsRoleDo> roleDoList = roleMapper.selectById(roleId);
        if(CollectionUtils.isEmpty(roleDoList)) {
            Asserts.fail("关联角色不存在");
        }
        // TODO 这步存在问题，应该每个建立关系的资源狗应该存在
        List<UmsResourceDo> resourceDoList = resourceMapper.selectByIdBatch(resourceIds);
        if(CollectionUtils.isEmpty(resourceDoList) && resourceDoList.size() == resourceIds.size()) {
            Asserts.fail("关联资源有不存在");
        }
        // 建立新的关系前，应该删除旧的关系
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
