package com.hzau.college.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.JsonVo;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.function.Function;

public abstract class BaseController<Po, ReqVo> {
    //此处必须是Po，因为MP方法是与数据库挂钩的，必须放入Po对象。调用MP的方法都要传入Po
    protected abstract IService<Po> getService();

    protected abstract Function<ReqVo, Po> getFunction();


    //前台传入以" , "分隔的一个字符串
    @PostMapping("/remove")
    public JsonVo remove(@NotNull(message = "id不能为空") String ids) {
        if (getService().removeByIds(Arrays.asList(ids.split(",")))) {
            return JsonVos.ok(CodeMsg.REMOVE_OK);
        }else {
            return JsonVos.raise(CodeMsg.REMOVE_ERROR);
        }
    }

    //前台传入的数据恰好可以组件一个reqVo对象(保存专用)，SpringBoot会自动拼接
    @PostMapping("/save")
    public JsonVo save(@Valid ReqVo reqVo) {
        //TODO reqVo -> po
        Po po = getFunction().apply(reqVo);
        if(getService().saveOrUpdate(po)){
            return JsonVos.ok(CodeMsg.SAVE_OK);
        }else {
            return JsonVos.raise(CodeMsg.SAVE_ERROR);
        }
    }
}
