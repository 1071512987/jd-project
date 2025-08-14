package com.hzau.college.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionDto {
    private String role;

    private List<String> permissions;
}
