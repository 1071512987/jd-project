package com.hzau.college.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.pojo.po.admin.AdminSysRole;
import com.hzau.college.pojo.vo.PageVo;
import com.hzau.college.pojo.vo.list.sys.SysRoleVo;
import com.hzau.college.pojo.vo.req.page.SysRolePageReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysRoleReqVo;

import java.util.List;
import java.util.Map;

public interface AdminSysRoleService extends IService<AdminSysRole> {

    PageVo<SysRoleVo> list(SysRolePageReqVo reqVo);

    List<Short> listIds(Integer userId);

    boolean saveOrUpdate(AdminSysRoleReqVo reqVo);

    List<AdminSysRole> listByUserId(Integer userId);

    Map<String, Short> getRoleMap();
}

