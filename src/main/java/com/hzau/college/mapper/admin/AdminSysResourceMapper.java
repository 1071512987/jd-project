package com.hzau.college.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzau.college.pojo.dto.RolePermissionDto;
import com.hzau.college.pojo.po.admin.AdminSysResource;

import java.util.List;

public interface AdminSysResourceMapper extends BaseMapper<AdminSysResource> {

    List<RolePermissionDto> getRolePermissionMap();
}

