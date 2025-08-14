package com.hzau.college.service.impl.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzau.college.common.enhance.MpLambdaQueryWrapper;
import com.hzau.college.mapper.admin.AdminSysUserRoleMapper;
import com.hzau.college.pojo.po.admin.AdminSysUserRole;
import com.hzau.college.service.admin.AdminSysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("adminSysUserRoleService")
public class AdminSysUserRoleServiceImpl extends ServiceImpl<AdminSysUserRoleMapper, AdminSysUserRole> implements AdminSysUserRoleService {

    @Resource
    private AdminSysUserRoleMapper mapper;

    @Override
    public boolean removeByUserId(Long userId) {
        if (userId == null || userId <= 0) return false;
        MpLambdaQueryWrapper<AdminSysUserRole> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(AdminSysUserRole::getUserId, userId);
        return baseMapper.delete(wrapper) > 0;
    }

}

