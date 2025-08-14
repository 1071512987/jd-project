package com.hzau.college.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.hzau.college.common.annotation.LoginSign;
import com.hzau.college.common.annotation.validator.Year;
import com.hzau.college.common.util.Constants;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.common.util.Jwts;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.DataJsonVo;
import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.list.*;
import com.hzau.college.pojo.vo.req.page.KeywordPageReqVo;
import com.hzau.college.service.BaseServiceService;
import com.hzau.college.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.hzau.college.common.util.Jwts.getRequestToken;

@RestController
@RequestMapping("api/base")
@Api("全局基本业务相关接口")
@Validated
public class BaseServiceController {

    @Resource
    private BaseServiceService service;
    @Resource
    private PostService postService;

    /**
     * 文章搜索
     * 权限无限制
     * //TODO 目前用的Go端
     * @param reqVo
     * @return
     */
    @GetMapping("/search")
    @ApiOperation("1. 【全局搜索】：搜索范围是所有院校、专业、文章")
    @ApiOperationSupport(order = 1)
    public DataJsonVo<ServiceSearchVo> listPreferSubject(@Valid KeywordPageReqVo reqVo)  {
        return JsonVos.ok(postService.search(reqVo));
    }

    /**
     *  全局json：获取时间戳和加密md5
     *  已登录用户
     * 无需传入信息，根据当前用户token自动获取查询userId
     * @param request 请求信息
     * @return 带有加密Md5和unix时间戳的返回体
     */
    @LoginSign
    @GetMapping("/config/getInfo")
    @ApiOperation("2. 【全局Json】：获取时间戳和加密md5【根据当前用户token自动查询】")
    @ApiOperationSupport(order = 2)
    @RequiresPermissions(Constants.Permission.LOGINUSER_LIST)
    public DataJsonVo<ConfigInfoVo> getConfigInfo(HttpServletRequest request){
        String token = getRequestToken(request);
        Long userId = Jwts.getId(token);
        return JsonVos.ok(service.getConfigInfo(userId));
    }

    /**
     * 全局json：获取全局json
     * 已登录用户
     * 无需传入信息，根据当前用户token自动获取查询userId
     * @param request 请求信息
     * @return 带有全局json的返回体
     */
    @LoginSign
    @GetMapping("/config/get")
    @ApiOperation("3. 【全局Json】：获取全局json【根据当前用户token自动查询】")
    @ApiOperationSupport(order = 3)
    @RequiresPermissions(Constants.Permission.LOGINUSER_LIST)
    public DataJsonVo<ConfigInfoVo> getConfig(HttpServletRequest request){
        String token = getRequestToken(request);
        Long userId = Jwts.getId(token);
        return JsonVos.ok(service.getConfig(userId));
    }

    /**
     * 全局Json：保存全局配置信息
     * 已登录用户
     * 传入前台json，根据当前用户token自动获取userId
     * @param request 请求信息
     * @param jsonConfig 前台传入json
     * @return 保存成功/保存失败
     */
    @LoginSign
    @PostMapping("/config/save")
    @ApiOperation("4. 【全局Json】：保存全局配置信息【根据当前用户token自动查询】")
    @ApiOperationSupport(order = 4)
    @RequiresPermissions(Constants.Permission.LOGINUSER_LIST)
    public JsonVo saveConfig(HttpServletRequest request,@RequestParam @NotNull(message = "配置信息不能为空") String jsonConfig){
        String token = getRequestToken(request);
        Long userId = Jwts.getId(token);
        if(service.saveConfig(userId, jsonConfig)){
            return JsonVos.ok(CodeMsg.SAVE_OK);
        }
        return JsonVos.raise(CodeMsg.SAVE_ERROR);
    }

    /**
     *  志愿表：获取某个表的时间戳和加密md5
     *  已登录用户
     *  传入志愿表id，根据当前用户token自动获取查询userId
     * @param request 请求信息
     * @param tableId 志愿表id
     * @return 带有加密Md5和unix时间戳的集合返回体
     */
    @LoginSign
    @GetMapping("/table/getInfo")
    @ApiOperation("5. 【志愿表】：获取时间戳和加密md5【根据当前用户token自动查询】")
    @ApiOperationSupport(order = 5)
    @RequiresPermissions(Constants.Permission.LOGINUSER_LIST)
    public DataJsonVo<GuidanceTableVo> getTableInfo(HttpServletRequest request,
                                                    @RequestParam @ApiParam("多个id以,分隔") @NotBlank(message = "志愿表id不能为空") String tableId){
        String token = getRequestToken(request);
        Long userId = Jwts.getId(token);
        return JsonVos.ok(service.getTableInfo(userId, tableId));
    }

    /**
     * 志愿表：获取全局json
     * 已登录用户
     * 传入志愿表id，根据当前用户token自动获取查询userId
     * @param request 请求信息
     * @param tableId 志愿表id
     * @return 带有全局json集合的返回体
     */
    @LoginSign
    @GetMapping("/table/get")
    @ApiOperation("6. 【志愿表】：获取json【根据当前用户token自动查询】")
    @ApiOperationSupport(order = 6)
    @RequiresPermissions(Constants.Permission.LOGINUSER_LIST)
    public DataJsonVo<GuidanceTableVo> getTable(HttpServletRequest request,
                                                @RequestParam @ApiParam("多个id以,分隔") @NotBlank(message = "志愿表id不能为空") String tableId){
        String token = getRequestToken(request);
        Long userId = Jwts.getId(token);
        return JsonVos.ok(service.getTable(userId, tableId));
    }

    /**
     * 志愿表：保存某一志愿表信息
     * 已登录用户
     * 传入前台json，和志愿表id，根据当前用户token自动获取userId
     * @param request 请求信息
     * @param tableId 志愿表id
     * @param json 前台传入json
     * @return 保存成功/保存失败
     */
    @LoginSign
    @PostMapping("/table/save")
    @ApiOperation("7. 【志愿表】：保存全局配置信息【根据当前用户token自动查询】")
    @ApiOperationSupport(order = 7)
    @RequiresPermissions(Constants.Permission.LOGINUSER_LIST)
    public JsonVo saveTable(HttpServletRequest request,
                            @RequestParam @ApiParam("只能更新单个志愿表id") @NotBlank(message = "志愿表id不能为空") String tableId,
                            @RequestParam @NotBlank(message = "配置信息不能为空")String json){
        String token = getRequestToken(request);
        Long userId = Jwts.getId(token);
        if(service.saveTable(userId, tableId, json)){
            return JsonVos.ok(CodeMsg.SAVE_OK);
        }
        return JsonVos.raise(CodeMsg.SAVE_ERROR);
    }

    /**
     * OSS:文件上传
     * 权限无限制
     *
     * @param file 文件
     * @return 上传后URL
     */
    @PostMapping("/oss/upload")
    @ApiOperation("8. 【OSS】：文件上传【仅图片】")
    @ApiOperationSupport(order = 8)
    @RequiresPermissions(Constants.Permission.LOGINUSER_LIST)
    public DataJsonVo<UploadVo> upload(@RequestParam("file") MultipartFile file)  {
        return JsonVos.ok(service.upload(file));
    }



    /**
     * 一分一段表：返回全部的一份一段表
     * 权限无限制
     *
     * @param year 年份
     * @return 当年的所有分段信息
     */
    @GetMapping("/score/list")
    @ApiOperation("9. 【一分一段】：返回一分一段表所有内容")
    @ApiOperationSupport(order = 9)
    public DataJsonVo<List<ScoreSectionVo>> listScore(@RequestParam @ApiParam("查询年份") @Year String year, HttpServletRequest request){
        return JsonVos.ok(service.listScore(year));
    }

}
