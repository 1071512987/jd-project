package com.hzau.college.service.impl.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzau.college.common.enhance.MpLambdaQueryWrapper;
import com.hzau.college.common.enhance.MpPage;
import com.hzau.college.common.mapStruct.MapStructs;
import com.hzau.college.common.util.Streams;
import com.hzau.college.mapper.admin.AdminSysResourceMapper;
import com.hzau.college.mapper.admin.AdminSysRoleResourceMapper;
import com.hzau.college.pojo.dto.RolePermissionDto;
import com.hzau.college.pojo.po.admin.AdminSysResource;
import com.hzau.college.pojo.po.admin.AdminSysRoleResource;
import com.hzau.college.pojo.vo.PageVo;
import com.hzau.college.pojo.vo.list.sys.SysResourceVo;
import com.hzau.college.pojo.vo.req.page.AdminSysResourcePageReqVo;
import com.hzau.college.service.admin.AdminSysResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Service("adminSysResourceService")
public class AdminSysResourceServiceImpl extends ServiceImpl<AdminSysResourceMapper, AdminSysResource> implements AdminSysResourceService {

    @Resource
    private AdminSysResourceMapper mapper;

    @Resource
    private AdminSysRoleResourceMapper roleResourceMapper;

    @Override
    @Transactional(readOnly = true)
    public PageVo<SysResourceVo> list(AdminSysResourcePageReqVo reqVo) {
        MpLambdaQueryWrapper<AdminSysResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(reqVo.getKeyword(),
                AdminSysResource::getName,
                AdminSysResource::getUri,
                AdminSysResource::getPermission);
        wrapper.orderByDesc(AdminSysResource::getId);
        return baseMapper
                .selectPage(new MpPage<>(reqVo), wrapper)
                .buildVo(MapStructs.INSTANCE::po2vo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Short> listIds(Integer roleId) {
        if (roleId == null || roleId <= 0) return null;

        MpLambdaQueryWrapper<AdminSysRoleResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(AdminSysRoleResource::getResourceId);
        wrapper.eq(AdminSysRoleResource::getRoleId, roleId);

        List<Object> ids = roleResourceMapper.selectObjs(wrapper);
        return Streams.map(ids, (id) -> ((Integer) id).shortValue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminSysResource> listByRoleIds(List<Short> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) return null;

        List<Short> ids = listIds(roleIds);
        if (CollectionUtils.isEmpty(ids)) return null;

        MpLambdaQueryWrapper<AdminSysResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(AdminSysResource::getId, ids);
        return baseMapper.selectList(wrapper);
    }

    private List<Short> listIds(List<Short> roleIds) {
        MpLambdaQueryWrapper<AdminSysRoleResource> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(AdminSysRoleResource::getResourceId);
        wrapper.in(AdminSysRoleResource::getRoleId, roleIds);

        List<Object> ids = roleResourceMapper.selectObjs(wrapper);
        return Streams.map(new HashSet<>(ids), (id) -> ((Integer) id).shortValue());
    }

    //返回角色与权限的映射表
    @Override
    public Map<String, List<String>> getRolePermissionMap() {
        List<RolePermissionDto> rolePermissionList = baseMapper.getRolePermissionMap();
        List<String> roles = Streams.map(rolePermissionList, RolePermissionDto::getRole);
        List<List<String>> permissionLists = Streams.map(rolePermissionList, RolePermissionDto::getPermissions);
        HashMap<String, List<String>> rolePermissionMap = new HashMap<>();
        for (int i = 0; i < roles.size(); i++) {
            rolePermissionMap.put(roles.get(i), permissionLists.get(i));
        }
        return rolePermissionMap;
    }
}

