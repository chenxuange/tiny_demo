package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.modules.ums.UmsAdminParam;
import com.example.tiny_demo.modules.ums.UpdateAdminPasswordParam;
import com.example.tiny_demo.modules.ums.mapper.UmsAdminMapper;
import com.example.tiny_demo.modules.ums.mapper.UmsRoleMapper;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResource;
import com.example.tiny_demo.modules.ums.model.UmsRoleDo;
import com.example.tiny_demo.modules.ums.service.UmsAdminService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsRoleMapper roleMapper;

    @Override
    public UmsAdminDO getAdminByUsername(String username) {
        return null;
    }

    @Override
    public UmsAdminDO register(UmsAdminParam umsAdminParam) {
        UmsAdminDO umsAdminDO = new UmsAdminDO();
        BeanUtils.copyProperties(umsAdminParam, umsAdminDO);
        // 先查询是否有同名用户
        List<UmsAdminDO> adminDOList = adminMapper.selectList(umsAdminDO);
        if(!CollectionUtils.isEmpty(adminDOList)) {
            // 已经存在用户
            return null;
        }
        umsAdminDO.setCreateTime(new Date());
        // 新增用户默认启用
        umsAdminDO.setStatus(1);
        // TODO 用户密码加密保存
        adminMapper.insert(umsAdminDO);
        return umsAdminDO;
    }

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public String refreshToken(String oldToken) {
        return null;
    }

    @Override
    public Page<UmsAdminDO> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public boolean update(Long id, UmsAdminDO admin) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        // TODO 用户缓存以后再说
        // 先查再删
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        // TODO 不太合理，应该先判断是否存在用户，否则用户不存在，返回仍旧为false
        // 不要一开始就把所有状况想清楚
        int count = adminMapper.deleteByIdBatch(ids);
        return count > 0; // 删除成功，则
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        return 0;
    }

    @Override
    public List<UmsRoleDo> getRoleList(Integer adminId) {
        return roleMapper.getRoleList(adminId);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        return null;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam updatePasswordParam) {
        return 0;
    }

    @Override
    public UmsAdminDO get(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if(CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }
}
