package com.example.tiny_demo.model;

import java.io.Serializable;
import java.util.Date;

public class UmsResource implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Date createTime;

    private String name;

    private String url;

    private String description;

    private Long categoryId;


}
