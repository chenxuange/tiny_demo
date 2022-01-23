package com.example.tiny_demo.service.impl;

import com.example.tiny_demo.dto.UmsAdminParam;
import com.example.tiny_demo.dto.UpdateAdminPasswordParam;
import com.example.tiny_demo.model.UmsAdmin;
import com.example.tiny_demo.model.UmsResource;
import com.example.tiny_demo.model.UmsRole;
import com.example.tiny_demo.service.UmsAdminService;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Override
    public UmsAdmin getAdminByUsername(String username) {

        return null;
    }

    @Override
    public UmsAdmin register(UmsAdminParam umsAdminParam) {
        return null;
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
    public Page<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public boolean update(Long id, UmsAdmin admin) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {
        return 0;
    }

    @Override
    public List<UmsRole> getRoleList(Long adminId) {
        return null;
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        return null;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam updatePasswordParam) {
        return 0;
    }
}
