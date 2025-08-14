package com.hzau.college.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.aliyun.oss.OSSClient;
import com.hzau.college.common.enhance.MpLambdaQueryWrapper;
import com.hzau.college.common.mapStruct.MapStructs;
import com.hzau.college.common.util.FileNames;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.common.util.Streams;
import com.hzau.college.mapper.ScoreSectionMapper;
import com.hzau.college.pojo.po.ScoreSection;
import com.hzau.college.pojo.vo.list.*;
import com.hzau.college.service.BaseServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hzau.college.common.util.Constants.Redis.*;

@Service("BaseServiceService")
@Slf4j
public class BaseServiceServiceImpl implements BaseServiceService {

    @Resource
    private ScoreSectionMapper scoreSectionMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Value("${aliyun.oss.maxSize}")
    private int maxSize;

    @Value("${aliyun.oss.bucketName}")
    private String ossBucketName;

    @Value("${aliyun.oss.dir.prefix}")
    private String ossDirPrefix;

    // Spring EL方式
    @Value("${aliyun.cdn:#{null}}")
    private String aliyunCdnDomain;

    @Resource
    private OSSClient ossClient;

    @Override
    public ConfigInfoVo getConfigInfo(Long userId) {
        ConfigInfoVo configInfoVo = new ConfigInfoVo();
        // 存入Redis，不设置过期时间
        String unix = stringRedisTemplate.opsForValue().get(REDIS_CONFIG_KEY + userId + REDIS_UNIX_SUF);
        String md5Json = stringRedisTemplate.opsForValue().get(REDIS_CONFIG_KEY + userId + REDIS_MD5_SUF);
        configInfoVo.setMd5Json(md5Json);
        if (unix != null){
            configInfoVo.setUnix(NumberUtil.parseLong(unix));
        }
        return configInfoVo;
    }

    @Override
    public ConfigInfoVo getConfig(Long userId) {
        ConfigInfoVo configInfoVo = new ConfigInfoVo();
        String json = stringRedisTemplate.opsForValue().get(REDIS_CONFIG_KEY + userId + REDIS_JSON_SUF);
        configInfoVo.setJson(json);
        return configInfoVo;
    }

    @Override
    @Transactional
    public boolean saveConfig(Long userId, String jsonConfig) {
        Digester digester = new Digester(DigestAlgorithm.MD5);
        String unix = Long.toString(new Date().getTime());
        try {
            // 存入当前时间戳
            stringRedisTemplate.opsForValue().set(REDIS_CONFIG_KEY + userId + REDIS_UNIX_SUF, unix);
            // 存入前端json
            stringRedisTemplate.opsForValue().set(REDIS_CONFIG_KEY + userId + REDIS_JSON_SUF, jsonConfig);
            // 存入md5Json
            String md5Json = digester.digestHex(jsonConfig);
            stringRedisTemplate.opsForValue().set(REDIS_CONFIG_KEY + userId + REDIS_MD5_SUF, md5Json);
        }catch (Exception e){
            return false;
        }
        return true;
    }


    @Override
    public GuidanceTableVo getTableInfo(Long userId, String tableId) {
        String[] ids = tableId.split(",");
        GuidanceTableVo res = new GuidanceTableVo();
        List<GuidanceTableInfoVo> tableVos = new ArrayList<>();
        for (String id : ids) {
            GuidanceTableInfoVo tableVo = new GuidanceTableInfoVo();
            tableVo.setTableId(id);
            String md5Json = stringRedisTemplate.opsForValue().get(REDIS_TABLE_KEY + userId + ":" + id + REDIS_MD5_SUF);
            String unix = stringRedisTemplate.opsForValue().get(REDIS_TABLE_KEY + userId + ":" + id + REDIS_UNIX_SUF);
            tableVo.setMd5Json(md5Json);
            if (unix != null){
                tableVo.setUnix(NumberUtil.parseLong(unix));
            }
            tableVos.add(tableVo);
        }
        res.setInfoVos(tableVos);
        return res;
    }

    @Override
    public GuidanceTableVo getTable(Long userId, String tableId) {
        String[] ids = tableId.split(",");
        GuidanceTableVo res = new GuidanceTableVo();
        List<GuidanceTableInfoVo> tableVos = new ArrayList<>();
        for (String id : ids) {
            GuidanceTableInfoVo tableVo = new GuidanceTableInfoVo();
            tableVo.setTableId(id);
            String s = REDIS_TABLE_KEY + userId + ":" + id + REDIS_JSON_SUF;
            String json = stringRedisTemplate.opsForValue().get(REDIS_TABLE_KEY + userId + ":" + id + REDIS_JSON_SUF);
            tableVo.setJson(json);
            tableVos.add(tableVo);
        }
        res.setInfoVos(tableVos);
        return res;
    }

    @Override
    @Transactional
    public boolean saveTable(Long userId, String tableId, String json) {
        if (tableId.split(",").length > 1) return JsonVos.raise(false, "只能更新单个志愿表id");
        Digester digester = new Digester(DigestAlgorithm.MD5);
        String unix = Long.toString(new Date().getTime());
        try {
            // 存入当前时间戳
            stringRedisTemplate.opsForValue().set(REDIS_TABLE_KEY + userId + ":" + tableId + REDIS_UNIX_SUF, unix);
            // 存入前端json
            stringRedisTemplate.opsForValue().set(REDIS_TABLE_KEY + userId + ":" + tableId + REDIS_JSON_SUF, json);
            // 存入md5Json
            String md5Json = digester.digestHex(json);
            stringRedisTemplate.opsForValue().set(REDIS_TABLE_KEY + userId + ":" + tableId + REDIS_MD5_SUF, md5Json);
        }catch (Exception e){
            return  false;
        }
        return true;
    }

//    /**
//     * 根据图片链接将其上传到 OSS
//     * @param url
//     * @return UploadVo
//     */
//    @Override
//    public UploadVo upload(String url) {
//        // codingmore/images/ + 2022年06月09日 + UUID + .jpg
//        String imgName = ossDirPrefix + FileNames.getImgName(url);
//        try (InputStream inputStream = new URL(url).openStream()) {
//            ossClient.putObject(ossBucketName, imgName, inputStream);
//        } catch (IOException e) {
//
//            log.error("根据外链上传图片到 OSS 出错了：", e);
////            Asserts.fail("上传图片到 OSS 出错了");
//        }
//        String imgUrl = getImgUrl(imgName);
//        UploadVo res = new UploadVo();
//        res.setUrl(imgUrl);
//        return  res;
//    }

    @Override
    public UploadVo upload(MultipartFile file) {
        try {
            String objectName = ossDirPrefix + FileNames.getImgName(file.getOriginalFilename());
            // 创建PutObject请求。
            ossClient.putObject(ossBucketName, objectName, file.getInputStream());
            String imgUrl = getImgUrl(objectName);
            UploadVo res = new UploadVo();
            res.setUrl(imgUrl);
            return res;
        } catch (IOException e) {
            log.error(e.getMessage());
            return JsonVos.raise(true, "文件上传失败");
        }
    }

    private String getImgUrl(String imgName) {
        if (aliyunCdnDomain == null) {
            return "https://"+ ossBucketName + "." + ossClient.getEndpoint().getHost()  + "/" + imgName;
        }
        return "https://" + aliyunCdnDomain  + "/" + imgName;
    }

//    @Override
//    public boolean needUpload(String imageUrl) {
//        if (imageUrl.indexOf(ossClient.getEndpoint().getHost()) != -1) {
//            return false;
//        }
//        if (aliyunCdnDomain != null && imageUrl.indexOf(aliyunCdnDomain) != -1) {
//            return false;
//        }
//        return true;
//    }


    @Override
    public List<ScoreSectionVo> listScore(String year) {
        MpLambdaQueryWrapper<ScoreSection> wrapper = new MpLambdaQueryWrapper<>();
        wrapper.eq(ScoreSection::getYear, year);
        return Streams.map(scoreSectionMapper.selectList(wrapper), MapStructs.INSTANCE::po2vo);
    }

}

