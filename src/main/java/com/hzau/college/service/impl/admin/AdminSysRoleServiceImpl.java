package com.hzau.college.service.impl.admin;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hzau.college.common.enhance.MpLambdaQueryWrapper;
import com.hzau.college.common.enhance.MpPage;
import com.hzau.college.common.mapStruct.MapStructs;
import com.hzau.college.common.util.Constants;
import com.hzau.college.common.util.Streams;
import com.hzau.college.common.util.Strings;
import com.hzau.college.mapper.admin.AdminSysRoleMapper;
import com.hzau.college.mapper.admin.AdminSysUserRoleMapper;
import com.hzau.college.pojo.po.admin.AdminSysRole;
import com.hzau.college.pojo.po.admin.AdminSysRoleResource;
import com.hzau.college.pojo.po.admin.AdminSysUserRole;
import com.hzau.college.pojo.vo.PageVo;
import com.hzau.college.pojo.vo.list.sys.SysRoleVo;
import com.hzau.college.pojo.vo.req.page.SysRolePageReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysRoleReqVo;
import com.hzau.college.service.admin.AdminSysRoleResourceService;
import com.hzau.college.service.admin.AdminSysRoleService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("adminSysRoleService")
public class AdminSysRoleServiceImpl extends ServiceImpl<AdminSysRoleMapper, AdminSysRole> implements AdminSysRoleService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AdminSysRoleResourceService roleResourceService;

    @Resource
    private AdminSysUserRoleMapper userRoleMapper;

    @Resource
    private AdminSysRoleMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public PageVo<SysRoleVo> list(SysRolePageReqVo reqVo) {
        MpLambdaQueryWrapper<AdminSysRole> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.like(reqVo.getKeyword(), AdminSysRole::getName);
        wrapper.orderByDesc(AdminSysRole::getId);
        return baseMapper
                .selectPage(new MpPage<>(reqVo), wrapper)
                .buildVo(MapStructs.INSTANCE::po2vo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Short> listIds(Integer userId) {
        if (userId == null || userId <= 0) return null;

        MpLambdaQueryWrapper<AdminSysUserRole> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.select(AdminSysUserRole::getRoleId);
        wrapper.eq(AdminSysUserRole::getUserId, userId);

        List<Object> ids = userRoleMapper.selectObjs(wrapper);
        return Streams.map(ids, (id) -> ((Integer) id).shortValue());
    }

    @Override
    public boolean saveOrUpdate(AdminSysRoleReqVo reqVo) {
        // 转成PO
        AdminSysRole po = MapStructs.INSTANCE.reqVo2po(reqVo);

        // 保存角色信息
        if (!saveOrUpdate(po)) return false;

        Short id = reqVo.getId();
        if (id != null && id > 0) {
            MpLambdaQueryWrapper<AdminSysUserRole> wrapper = new MpLambdaQueryWrapper<>();
            wrapper.select(AdminSysUserRole::getUserId);
            wrapper.eq(AdminSysUserRole::getRoleId, id);
            List<Object> userIds = userRoleMapper.selectObjs(wrapper);
            if (!CollectionUtils.isEmpty(userIds)) {
                for (Object userId : userIds) {
                    // 将拥有这个角色的用户从缓存中移除（让Redis中token失效，用户必须重新登录）
                    stringRedisTemplate.delete(Constants.Redis.REDIS_FRONT_LOGIN_TOKEN_KEY + userId);
                }
            }

            // 删除当前角色的所有资源信息
            roleResourceService.removeByRoleId(id);
        }

        // 保存角色信息
        String resourceIdsStr = reqVo.getResourceIds();
        if (Strings.isEmpty(resourceIdsStr)) return true;

        String[] resourceIds = resourceIdsStr.split(",");
        List<AdminSysRoleResource> roleResources = new ArrayList<>();
        Short roleId = po.getId();
        for (String resourceId : resourceIds) { // 构建SysUserRole对象
            AdminSysRoleResource roleResource = new AdminSysRoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(Short.parseShort(resourceId));
            roleResources.add(roleResource);
        }
        return roleResourceService.saveBatch(roleResources);
    }

    /**
     * 获取 角色名：角色id 的Map
     * @return HashMap
     */
    @Override
    public Map<String, Short> getRoleMap() {
        List<AdminSysRole> roles = query().list();
        HashMap<String, Short> map = new HashMap<>();
        for (AdminSysRole role : roles) {
            map.put(role.getName(), role.getId());
        }
        return map;
    }




    @Override
    @Transactional(readOnly = true)
    public List<AdminSysRole> listByUserId(Integer userId) {
        if (userId == null || userId <= 0) return null;
        List<Short> ids = listIds(userId);
        if (CollectionUtils.isEmpty(ids)) return null;

        MpLambdaQueryWrapper<AdminSysRole> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.in(AdminSysRole::getId, ids);

//        String sql = "SELECT role_id FROM sys_user_role WHERE user_id = " + userId;
//        wrapper.inSql(SysRole::getId, sql);
        return  baseMapper.selectList(wrapper);
    }



    @Override
    @Transactional(readOnly = true)
    public List<AdminSysRole> list() {
        MpLambdaQueryWrapper<AdminSysRole> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.orderByDesc(AdminSysRole::getId);
        return super.list(wrapper);
    }
}

