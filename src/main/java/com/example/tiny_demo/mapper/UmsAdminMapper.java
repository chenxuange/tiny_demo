package com.example.tiny_demo.mapper;

import com.example.tiny_demo.model.UmsAdmin;
import com.example.tiny_demo.query.UmsAdminQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsAdminMapper {

    List<UmsAdmin> selectList(@Param("umsAdmin") UmsAdminQuery umsAdmin);
}
