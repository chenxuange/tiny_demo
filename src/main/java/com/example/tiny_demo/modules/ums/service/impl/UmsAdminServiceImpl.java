package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.api.ResultCode;
import com.example.tiny_demo.common.exception.Asserts;
import com.example.tiny_demo.modules.ums.dto.UmsAdminLoginParam;
import com.example.tiny_demo.modules.ums.dto.UmsAdminParam;
import com.example.tiny_demo.modules.ums.dto.UpdateAdminPasswordParam;
import com.example.tiny_demo.modules.ums.mapper.UmsAdminMapper;
import com.example.tiny_demo.modules.ums.mapper.UmsAdminRoleRMapper;
import com.example.tiny_demo.modules.ums.mapper.UmsRoleMapper;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsAdminRoleR;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import com.example.tiny_demo.modules.ums.model.UmsRoleDo;
import com.example.tiny_demo.modules.ums.service.UmsAdminService;
import com.example.tiny_demo.security.utils.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminRoleRMapper adminRoleRMapper;

    @Override
    public UmsAdminDO getAdminByUsername(String username) {
        return null;
    }

    @Override
    public UmsAdminDO register(UmsAdminParam umsAdminParam) {
        UmsAdminDO umsAdminDO = new UmsAdminDO();
        BeanUtils.copyProperties(umsAdminParam, umsAdminDO);
        // ??????????????????????????????
        List<UmsAdminDO> adminDOList = adminMapper.selectList(umsAdminDO);
        if (!CollectionUtils.isEmpty(adminDOList)) {
            // ??????????????????
            Asserts.fail(ResultCode.ADMIN_EXIST_ERROR);
        }
        // ?????????????????????
        umsAdminDO.setCreateTime(new Date());
        // ????????????????????????
        umsAdminDO.setStatus(1);
        //  ????????????????????????
        umsAdminDO.setPassword(passwordEncoder.encode(umsAdminDO.getPassword()));
        adminMapper.insert(umsAdminDO);
        return umsAdminDO;
    }


    @Override
    public String refreshToken(String oldToken) {
        return null;
    }

    @Override
    public PageInfo<UmsAdminDO> list(String keyword, Integer pageNum, Integer pageSize) {
        PageInfo<UmsAdminDO> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> adminMapper.selectByKeyword(keyword));
        return pageInfo;
    }

    @Override
    public boolean update(Long id, UmsAdminDO admin) {
        return false;
    }

    @Override
    public void delete(Integer id) {
        // TODO ????????????????????????
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            Asserts.fail("??????????????????");
        }
        // ??????????????????????????????????????????
         adminMapper.deleteByIdBatch(ids);
    }

    @Override
    public void updateRole(Integer adminId, List<Integer> roleIds) {
        // ????????????????????????????????????????????????????????????????????????????????????????????????
        // ???????????????????????????
        adminRoleRMapper.delete(adminId);
        List<UmsAdminRoleR> adminRoleRList = roleIds.stream().map(roleId -> {
            UmsAdminRoleR umsAdminRoleR = new UmsAdminRoleR();
            umsAdminRoleR.setAdminId(adminId);
            umsAdminRoleR.setRoleId(roleId);
            return umsAdminRoleR;
        }).collect(Collectors.toList());
        // ??????????????????????????????????????????id?????????????????????????????????id???????????????????????????
        adminRoleRMapper.insertBatch(adminRoleRList);
    }

    @Override
    public List<UmsRoleDo> getRoleList(Integer adminId) {
        return roleMapper.getRoleList(adminId);
    }

    @Override
    public List<UmsResourceDo> getResourceList(Long adminId) {
        return null;
    }

    @Override
    public void updatePassword(UpdateAdminPasswordParam updatePasswordParam) {
        // ???????????????????????????????????????????????????
        // ????????????
        UmsAdminDO adminDO = new UmsAdminDO();
        adminDO.setUsername(updatePasswordParam.getUsername());
        List<UmsAdminDO> adminDOList = adminMapper.selectList(adminDO);
        if (CollectionUtils.isEmpty(adminDOList)) {
            Asserts.fail("??????????????????");
        }
        UmsAdminDO umsAdminDO = adminDOList.get(0);
        // ????????????
        if (!passwordEncoder.matches(updatePasswordParam.getOldPassword(), umsAdminDO.getPassword())) {
            Asserts.fail("??????????????????");
        }
        // ????????????
        umsAdminDO.setPassword(passwordEncoder.encode(updatePasswordParam.getNewPassword()));
        adminMapper.updateById(umsAdminDO);
    }

    @Override
    public UmsAdminDO get(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public UmsAdminDO updateUser(Integer id, UmsAdminParam adminParam) {
        // TODO ???????????????
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            Asserts.fail("??????????????????");
        }
        UmsAdminDO adminDO = new UmsAdminDO();
        BeanUtils.copyProperties(adminParam, adminDO);
        adminDO.setId(id);
        adminMapper.updateById(adminDO);
        return adminDO;
    }

    @Override
    public String login(UmsAdminLoginParam adminLoginParam) {
        String token = null;
        try {
            // TODO ?????????????????????loadUserByUsername?????????
            UmsAdminDO condition = new UmsAdminDO();
            condition.setUsername(adminLoginParam.getUsername());
            List<UmsAdminDO> list = adminMapper.selectList(condition);
            if (CollectionUtils.isEmpty(list)) {
                Asserts.fail("??????????????????");
            }
            if (!passwordEncoder.matches(adminLoginParam.getPassword(), list.get(0).getPassword())) {
                Asserts.fail("????????????");
            }
            if (list.get(0).getStatus() == 0) {
                Asserts.fail("?????????????????????");
            }
            Map<String, Object> claims = new HashMap<>();
            // TODO ????????????????????????
            String CLAIM_KEY_USERNAME = "sub";
            String CLAIM_KEY_CREATED = "created";
            claims.put(CLAIM_KEY_USERNAME, list.get(0).getUsername());
            claims.put(CLAIM_KEY_CREATED, new Date());
            // ??????token
            token = jwtTokenUtil.generateToken(claims);
        } catch (AuthenticationException e) {
            logger.warn("???????????????{}", e.getMessage());
        }
        return token;
    }
}
