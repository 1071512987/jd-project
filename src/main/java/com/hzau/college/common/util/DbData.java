package com.hzau.college.common.util;

import com.hzau.college.service.admin.AdminSysResourceService;
import com.hzau.college.service.admin.AdminSysRoleService;
import com.hzau.college.service.front.FrontSysResourceService;
import com.hzau.college.service.front.FrontSysRoleService;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author 半夜里咳嗽的狼
 * @Date 2023/1/19 21:08
 * @Description: 自动从数据库查询一些固定数据，以便其他类访问
 */
@Component
public class DbData {

    @Resource
    private FrontSysResourceService frontResourceService;
    @Resource
    private AdminSysResourceService adminResourceService;

    @Resource
    private FrontSysRoleService frontRoleService;
    @Resource
    private AdminSysRoleService adminRoleService;


    // 前台、后台角色权限映射表（数量有限且一般固定，项目部署时直接静态存储），只从中get没有线程问题
    @Getter
    private Map<String, List<String>> frontRolePermissionsMap;
    @Getter
    private Map<String, List<String>> adminRolePermissionsMap;


    // 将前台、后台 角色名:角色id Map写死在内存中，每次从内存中获取
    @Getter
    private Map<String, Short> frontRoleMap;
    @Getter
    private Map<String, Short> adminRoleMap;


    @PostConstruct
    public void init(){
        frontRolePermissionsMap = frontResourceService.getRolePermissionMap();
        adminRolePermissionsMap = adminResourceService.getRolePermissionMap();
        frontRoleMap = frontRoleService.getRoleMap();
        adminRoleMap = adminRoleService.getRoleMap();

    }


}
