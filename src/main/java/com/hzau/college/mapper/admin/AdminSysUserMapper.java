package com.hzau.college.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hzau.college.pojo.dto.SysUserAndRolesDto;
import com.hzau.college.pojo.po.admin.AdminSysUser;
import org.apache.ibatis.annotations.Options;

public interface AdminSysUserMapper extends BaseMapper<AdminSysUser> {

    SysUserAndRolesDto getRolesByName(String username);

    SysUserAndRolesDto getRolesByPhone(String phone);

    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(AdminSysUser user);

}

