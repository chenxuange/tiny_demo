package com.example.tiny_demo.query;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class UmsAdminQuery {
    private Long id;

    private String username;

    private String password;

    private String icon;

    private String email;

    private String nickName;

    private String note;

    private Date createTime;

    private Date loginTime;

    private Integer status;

}
