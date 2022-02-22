package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsAdminLoginLog;

public interface UmsAdminLoginLogMapper {

    /**
     * 新增登录日志
     * @param adminLoginLog
     */
    void insert(UmsAdminLoginLog adminLoginLog);
}
