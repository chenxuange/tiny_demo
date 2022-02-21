package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.api.ResultCode;
import com.example.tiny_demo.common.exception.Asserts;
import com.example.tiny_demo.modules.ums.dto.UmsMenuNode;
import com.example.tiny_demo.modules.ums.mapper.UmsMenuMapper;
import com.example.tiny_demo.modules.ums.model.UmsMenuDo;
import com.example.tiny_demo.modules.ums.service.UmsMenuService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UmsMenuServiceImpl implements UmsMenuService {
    @Autowired
    private UmsMenuMapper menuMapper;

    @Override
    public UmsMenuDo selectById(Integer id) {
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<UmsMenuDo> umsMenuDoList = menuMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(umsMenuDoList)) {
            return null;
        }
        return umsMenuDoList.get(0);
    }

    @Override
    public List<UmsMenuDo> selectPage(Integer parentId, Integer pageNum, Integer pageSize) {
        // 设置分页, 实际是，先对原查询方法名添加后缀_COUNT并执行衍生查询总数，再去执行原查询
        // 注意：pageNum默认值必须为1
        PageHelper.startPage(pageNum, pageSize);
        UmsMenuDo umsMenuDo = new UmsMenuDo();
        umsMenuDo.setParentId(parentId);
        return menuMapper.selectBatch(umsMenuDo);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        // 查出所有菜单
        List<UmsMenuDo> menuDoList = menuMapper.selectBatch(new UmsMenuDo());
        // 一级菜单
        List<UmsMenuNode> list = menuDoList.stream().filter(ele -> ele.getParentId() == 0).map(ele -> generateMenuNode(ele, menuDoList)).collect(Collectors.toList());
        return list;
    }

    /**
     * 生成树型菜单,递归构造
     * @param menu       父级菜单
     * @param menuDoList 所有菜单列表
     * @return
     */
    private UmsMenuNode generateMenuNode(UmsMenuDo menu, List<UmsMenuDo> menuDoList) {
        UmsMenuNode umsMenuNode = new UmsMenuNode();
        BeanUtils.copyProperties(menu, umsMenuNode);
        List<UmsMenuNode> children = menuDoList.stream().filter(sub -> Objects.equals(sub.getParentId(), menu.getId()))
                .map(submenu -> generateMenuNode(submenu, menuDoList)).collect(Collectors.toList());
        // 若是没找到子菜单，children也是一个空列表，不会是null
        umsMenuNode.setChildren(children);
        return umsMenuNode;
    }

    @Override
    public void create(UmsMenuDo umsMenuDo) {
        ArrayList<UmsMenuDo> menuList = new ArrayList<>();
        menuList.add(umsMenuDo);
        menuMapper.insert(menuList);
    }

    @Override
    public void delete(Integer id) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(id);
        menuMapper.deleteByIdBatch(list);
    }

    @Override
    public void update(Integer id, UmsMenuDo umsMenuDo) {
        // 更新菜单前，先查询菜单判断存在
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(id);
        List<UmsMenuDo> menuDoList = menuMapper.selectByIdBatch(integers);
        if(CollectionUtils.isEmpty(menuDoList)) {
            Asserts.fail(ResultCode.MENU_NOT_FOUND);
        }else{
            umsMenuDo.setId(id);
            menuMapper.update(umsMenuDo);
        }
    }

    @Override
    public void updateHidden(Integer id, Integer hidden) {
        // 更新菜单前，先查询菜单判断存在
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(id);
        List<UmsMenuDo> menuDoList = menuMapper.selectByIdBatch(integers);
        if(CollectionUtils.isEmpty(menuDoList)) {
            Asserts.fail(ResultCode.MENU_NOT_FOUND);
        }else{
            UmsMenuDo umsMenuDo = new UmsMenuDo();
            umsMenuDo.setId(id);
            umsMenuDo.setHidden(hidden);
            menuMapper.update(umsMenuDo);
        }
    }
}
