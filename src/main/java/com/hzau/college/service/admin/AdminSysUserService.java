package com.hzau.college.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.pojo.dto.SysUserAndRolesDto;
import com.hzau.college.pojo.po.admin.AdminSysUser;
import com.hzau.college.pojo.vo.PageVo;
import com.hzau.college.pojo.vo.list.sys.SysUserVo;
import com.hzau.college.pojo.vo.req.page.AdminSysUserPageReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysUserReqVo;

public interface AdminSysUserService extends IService<AdminSysUser> {

    PageVo<SysUserVo> list(AdminSysUserPageReqVo reqVo);

    boolean saveOrUpdate(AdminSysUserReqVo reqVo);

    SysUserAndRolesDto getRolesByName(String username);

    SysUserAndRolesDto getRolesByPhone(String phone);

}

