package com.hzau.college.service.impl.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzau.college.common.enhance.MpLambdaQueryWrapper;
import com.hzau.college.mapper.admin.AdminSysRoleResourceMapper;
import com.hzau.college.pojo.po.admin.AdminSysRoleResource;
import com.hzau.college.service.admin.AdminSysRoleResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("adminSysRoleResourceService")
public class AdminSysRoleResourceServiceImpl extends ServiceImpl<AdminSysRoleResourceMapper, AdminSysRoleResource> implements AdminSysRoleResourceService {

    @Resource
    private AdminSysRoleResourceMapper mapper;

    @Override
    public boolean removeByRoleId(Short roleId) {
        if (roleId == null || roleId <= 0) return false;
        MpLambdaQueryWrapper<AdminSysRoleResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(AdminSysRoleResource::getRoleId, roleId);
        return baseMapper.delete(wrapper) > 0;
    }

}

