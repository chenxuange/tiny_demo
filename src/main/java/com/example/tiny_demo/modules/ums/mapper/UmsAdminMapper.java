package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsAdmin;
import com.example.tiny_demo.modules.ums.query.UmsAdminQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsAdminMapper {

    List<UmsAdmin> selectList(@Param("umsAdmin") UmsAdminQuery umsAdmin);
}
