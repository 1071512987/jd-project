package com.hzau.college.service.impl.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzau.college.common.enhance.MpLambdaQueryWrapper;
import com.hzau.college.common.enhance.MpPage;
import com.hzau.college.common.mapStruct.MapStructs;
import com.hzau.college.common.util.Constants;
import com.hzau.college.common.util.Strings;
import com.hzau.college.mapper.admin.AdminSysUserMapper;
import com.hzau.college.pojo.dto.SysUserAndRolesDto;
import com.hzau.college.pojo.po.admin.AdminSysUser;
import com.hzau.college.pojo.po.admin.AdminSysUserRole;
import com.hzau.college.pojo.vo.PageVo;
import com.hzau.college.pojo.vo.list.sys.SysUserVo;
import com.hzau.college.pojo.vo.req.page.AdminSysUserPageReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysUserReqVo;
import com.hzau.college.service.admin.AdminSysUserRoleService;
import com.hzau.college.service.admin.AdminSysUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("adminSysUserService")
public class AdminSysUserServiceImpl extends ServiceImpl<AdminSysUserMapper, AdminSysUser> implements AdminSysUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AdminSysUserRoleService userRoleService;


    @Override
    @Transactional(readOnly = true)
    public PageVo<SysUserVo> list(AdminSysUserPageReqVo reqVo) {
        MpLambdaQueryWrapper<AdminSysUser> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(reqVo.getKeyword(), AdminSysUser::getNickname, AdminSysUser::getUsername);
        wrapper.orderByDesc(AdminSysUser::getId);
        return baseMapper
                .selectPage(new MpPage<>(reqVo), wrapper)
                .buildVo(MapStructs.INSTANCE::po2vo);
    }

    @Override
    public boolean saveOrUpdate(AdminSysUserReqVo reqVo) {
        // 转成PO
        AdminSysUser po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 保存用户信息
        if (!saveOrUpdate(po)) return false;

        Long id = reqVo.getId();
        if (id != null && id > 0) { // 如果是做更新
            // 将更新成功的用户从缓存中移除（让Redis中token失效，用户必须重新登录）
            stringRedisTemplate.delete(Constants.Redis.REDIS_FRONT_LOGIN_TOKEN_KEY + id);
            // 删除当前用户的所有角色信息
            userRoleService.removeByUserId(reqVo.getId());
        }

        // 保存角色信息
        String roleIdsStr = reqVo.getRoleIds();
        if (Strings.isEmpty(roleIdsStr)) return true;

        String[] roleIds = roleIdsStr.split(",");
        List<AdminSysUserRole> userRoles = new ArrayList<>();
        Long userId = po.getId();
        for (String roleId : roleIds) { // 构建AdminSysUserRole对象
            AdminSysUserRole userRole = new AdminSysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(Short.parseShort(roleId));
            userRoles.add(userRole);
        }
        return userRoleService.saveBatch(userRoles);
    }

    @Override
    // 查询一名用户的user信息和角色信息，用于JWT校验
    public SysUserAndRolesDto getRolesByName(String username) {
        return baseMapper.getRolesByName(username);
    }

    @Override
    public SysUserAndRolesDto getRolesByPhone(String phone) {
        return baseMapper.getRolesByPhone(phone);
    }
}

