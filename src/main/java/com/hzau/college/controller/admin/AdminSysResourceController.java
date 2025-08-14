package com.hzau.college.controller.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.common.mapStruct.MapStructs;
import com.hzau.college.common.util.Constants;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.controller.BaseController;
import com.hzau.college.pojo.po.admin.AdminSysResource;
import com.hzau.college.pojo.vo.DataJsonVo;
import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.PageJsonVo;
import com.hzau.college.pojo.vo.list.sys.SysResourceVo;
import com.hzau.college.pojo.vo.req.page.AdminSysResourcePageReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysResourceReqVo;
import com.hzau.college.service.admin.AdminSysResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("api/adminSysResources")
@Api("后台资源信息相关接口")
public class AdminSysResourceController extends BaseController<AdminSysResource, AdminSysResourceReqVo> {
    
    @Resource
    private AdminSysResourceService service;

    @Override
    protected IService<AdminSysResource> getService() {
        return service;
    }

    @Override
    protected Function<AdminSysResourceReqVo, AdminSysResource> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }

    @GetMapping("/ids")
    @ApiOperation("根据用户id获取角色id")
    @RequiresPermissions(Constants.Permission.SYS_RESOURCE_LIST)
    public DataJsonVo<List<Short>> ids(Integer roleId) {
        return JsonVos.ok(service.listIds(roleId));
    }

    @GetMapping
    @ApiOperation("分页查询")
    @RequiresPermissions(Constants.Permission.SYS_RESOURCE_LIST)
    public PageJsonVo<SysResourceVo> list(AdminSysResourcePageReqVo reqVo) {
        return JsonVos.ok(service.list(reqVo));
    }

//    @GetMapping("/list")
//    @ApiOperation("查询所有")
//    @RequiresPermissions(Constants.Permisson.SYS_RESOURCE_LIST)
//    public DataJsonVo<List<SysResourceVo>> list() {
//        return JsonVos.ok(Streams.map(service.list(), MapStructs.INSTANCE::po2vo));
//    }
//
//    @GetMapping("/listTree")
//    @ApiOperation("查询所有（树状结构结构展示）")
//    @RequiresPermissions(Constants.Permisson.SYS_RESOURCE_LIST)
//    public DataJsonVo<List<SysResourceTreeVo>> listTree() {
//        return JsonVos.ok(service.listTree());
//    }
//
//    @GetMapping("/listParents")
//    @ApiOperation("查询所有的父资源（目录、菜单）")
//    @RequiresPermissions(Constants.Permisson.SYS_RESOURCE_LIST)
//    public DataJsonVo<List<SysResourceVo>> listParents() {
//        return JsonVos.ok(service.listParents());
//    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_RESOURCE_SAVEORUPDATE)
    public JsonVo save(AdminSysResourceReqVo sysResourceReqVo) {
        return super.save(sysResourceReqVo);
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_RESOURCE_REMOVE)
    public JsonVo remove(String id) {
        return super.remove(id);
    }



}

