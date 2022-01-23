package com.example.tiny_demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UmsAdmin implements Serializable {

    private static final long serialVersionUID=1L;

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

