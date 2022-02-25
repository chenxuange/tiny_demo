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
    public void create(UmsRoleParam roleParam) {
        // 实际数据库中角色表中应该给name加唯一约束
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
    }

    @Override
    public void update(Integer id, UmsRoleParam roleParam) {
        List<UmsRoleDo> list = roleMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("该角色不存在");
        }
        UmsRoleDo roleDo = new UmsRoleDo();
        BeanUtils.copyProperties(roleParam, roleDo);
        roleDo.setId(id);
        roleMapper.updateById(roleDo);
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        List<UmsRoleDo> list = roleMapper.selectById(id);
        if(CollectionUtils.isEmpty(list)) {
            Asserts.fail("该角色不存在");
        }
        UmsRoleDo roleDo = new UmsRoleDo();
        roleDo.setId(id);
        roleDo.setStatus(status);
        roleMapper.updateById(roleDo);
        // TODO 移除资源缓存. 角色状态可能变为关停，那么下属资源不可用，导致用户资源变更
    }

    @Override
    public void deleteBatch(List<Integer> ids) {
        roleMapper.deleteBatchIds(ids);
        // TODO 移除资源缓存。删除角色时，用户不变更，但用户资源发生变更
    }

    @Override
    public void allocMenu(Integer roleId, List<Integer> menuIds) {
        // 先判断角色id是否存在，再判断菜单id是否存在
        List<UmsRoleDo> roleDoList = roleMapper.selectById(roleId);
        if(CollectionUtils.isEmpty(roleDoList)) {
            Asserts.fail("传入的角色id有误");
        }
        List<UmsMenuDo> menuDoList = menuMapper.selectByIdBatch(menuIds);
        if(CollectionUtils.isEmpty(menuDoList) && menuDoList.size() == menuIds.size()) {
            // 本地执行严格匹配策略，即传入的菜单id必须库中存在，有一个对应不上就提示错误
            Asserts.fail("传入的菜单id列表中有误");
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
    }

    @Override
    public void allocResource(Integer roleId, List<Integer> resourceIds) {
        // 先判断角色id是否存在，再判断资源id是否存在
        List<UmsRoleDo> roleDoList = roleMapper.selectById(roleId);
        if(CollectionUtils.isEmpty(roleDoList)) {
            Asserts.fail("传入的角色id有误");
        }
        List<UmsResourceDo> resourceDoList = resourceMapper.selectByIdBatch(resourceIds);
        if(CollectionUtils.isEmpty(resourceDoList) && resourceDoList.size() == resourceIds.size()) {
            // 严格保证，每个资源id都存在
            Asserts.fail("传入的资源id列表中有误");
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
        // TODO 移除用户资源。角色重新分配资源，导致用户资源变更
    }


}
