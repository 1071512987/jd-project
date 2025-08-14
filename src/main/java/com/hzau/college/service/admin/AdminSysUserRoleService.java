package com.hzau.college.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.pojo.po.admin.AdminSysUserRole;

public interface AdminSysUserRoleService extends IService<AdminSysUserRole> {

    boolean removeByUserId(Long userId);
}

