package com.hzau.college.controller.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.common.mapStruct.MapStructs;
import com.hzau.college.common.util.Constants;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.controller.BaseController;
import com.hzau.college.pojo.po.admin.AdminSysUser;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.PageJsonVo;
import com.hzau.college.pojo.vo.list.sys.SysUserVo;
import com.hzau.college.pojo.vo.req.page.AdminSysUserPageReqVo;
import com.hzau.college.pojo.vo.req.save.admin.AdminSysUserReqVo;
import com.hzau.college.service.admin.AdminSysUserService;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.function.Function;

@RestController
@RequestMapping("api/adminSysUsers")
@Api("前台用户信息相关接口")
public class AdminSysUserController extends BaseController<AdminSysUser, AdminSysUserReqVo> {
    
    @Resource
    private AdminSysUserService service;

    @Override
    protected IService<AdminSysUser> getService() {
        return service;
    }

    @Override
    protected Function<AdminSysUserReqVo, AdminSysUser> getFunction() {
        return MapStructs.INSTANCE::reqVo2po;
    }

    @GetMapping("/captcha")
    @ApiOperation("生成验证码")
    public void captcha(HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    @GetMapping
    @ApiOperation("分页查询")
    @RequiresPermissions(Constants.Permission.SYS_USER_LIST)
    public PageJsonVo<SysUserVo> list(AdminSysUserPageReqVo reqVo) {
        return JsonVos.ok(service.list(reqVo));
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_USER_SAVEORUPDATE)
    public JsonVo save(AdminSysUserReqVo reqVo) {
        if (service.saveOrUpdate(reqVo)) {
            return JsonVos.ok(CodeMsg.SAVE_OK);
        } else {
            return JsonVos.raise(CodeMsg.SAVE_ERROR);
        }
    }

    @Override
    @RequiresPermissions(Constants.Permission.SYS_USER_REMOVE)
    public JsonVo remove(String id) {
        return super.remove(id);
    }


}

