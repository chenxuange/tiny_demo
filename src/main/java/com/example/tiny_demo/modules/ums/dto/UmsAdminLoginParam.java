package com.example.tiny_demo.modules.ums.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UmsAdminLoginParam {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}

