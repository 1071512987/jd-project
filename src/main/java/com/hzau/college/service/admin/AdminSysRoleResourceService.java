package com.hzau.college.service.admin;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.pojo.po.admin.AdminSysRoleResource;

public interface AdminSysRoleResourceService extends IService<AdminSysRoleResource> {

    boolean removeByRoleId(Short roleId);

}

