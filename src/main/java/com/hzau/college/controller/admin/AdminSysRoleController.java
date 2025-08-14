package com.hzau.college.controller.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.common.mapStruct.MapStructs;
import com.hzau.college.common.util.Constants;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.common.util.Streams;
import com.hzau.college.controller.BaseController;
import com.hzau.college.pojo.po.admin.AdminSysRole;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.DataJsonVo;
import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.PageJsonVo;
import com.hzau.college.pojo.vo.list.sys.SysRoleVo;
import com.hzau.college.pojo.vo.req.page.SysRolePageReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysRoleReqVo;
import com.hzau.college.service.admin.AdminSysRoleService;
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
@RequestMapping("api/adminSysRoles")
@Api("前台角色信息相关接口")
public class AdminSysRoleController extends BaseController<AdminSysRole, AdminSysRoleReqVo> {
    
    @Resource
    private AdminSysRoleService service;

    @Override
    protected IService<AdminSysRole> getService() {
        return service;
    }

    @Override
    protected Function<AdminSysRoleReqVo, AdminSysRole> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }

    @GetMapping("/ids")
    @ApiOperation("根据用户id获取角色id")
    @RequiresPermissions(Constants.Permission.SYS_ROLE_LIST)
    public DataJsonVo<List<Short>> ids(Integer userId) {
        return JsonVos.ok(service.listIds(userId));
    }

    @GetMapping("/list")
    @ApiOperation("查询所有")
    @RequiresPermissions(Constants.Permission.SYS_ROLE_LIST)
    public DataJsonVo<List<SysRoleVo>> list() {
        return JsonVos.ok(Streams.map(service.list(), MapStructs.INSTANCE::po2vo));
    }

    @GetMapping
    @ApiOperation("分页查询")
    @RequiresPermissions(Constants.Permission.SYS_ROLE_LIST)
    public PageJsonVo<SysRoleVo> list(SysRolePageReqVo reqVo) {
        return JsonVos.ok(service.list(reqVo));
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_ROLE_SAVEORUPDATE)
    public JsonVo save(AdminSysRoleReqVo reqVo) {
        if (service.saveOrUpdate(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.raise(CodeMsg.SAVE_ERROR);
        }
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_ROLE_REMOVE)
    public JsonVo remove(String id) {
        return super.remove(id);
    }


}

