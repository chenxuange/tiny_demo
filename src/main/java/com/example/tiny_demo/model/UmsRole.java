package com.example.tiny_demo.model;

import java.io.Serializable;
import java.util.Date;

public class UmsRole implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private String name;

    private String description;

    private Integer adminCount;

    private Date createTime;

    private Integer status;

    private Integer sort;


}

