package com.hzau.college.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.pojo.po.admin.AdminSysResource;
import com.hzau.college.pojo.vo.PageVo;
import com.hzau.college.pojo.vo.list.sys.SysResourceVo;
import com.hzau.college.pojo.vo.req.page.AdminSysResourcePageReqVo;

import java.util.List;
import java.util.Map;

public interface AdminSysResourceService extends IService<AdminSysResource> {

    PageVo<SysResourceVo> list(AdminSysResourcePageReqVo reqVo);

    List<Short> listIds(Integer roleId);

    List<AdminSysResource> listByRoleIds(List<Short> roleIds);

    Map<String, List<String>> getRolePermissionMap();
}

