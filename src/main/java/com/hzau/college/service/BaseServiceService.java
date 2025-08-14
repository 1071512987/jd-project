package com.hzau.college.service;

import com.hzau.college.pojo.vo.list.ConfigInfoVo;
import com.hzau.college.pojo.vo.list.GuidanceTableVo;
import com.hzau.college.pojo.vo.list.ScoreSectionVo;
import com.hzau.college.pojo.vo.list.UploadVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BaseServiceService {

    ConfigInfoVo getConfigInfo(Long userId);

    boolean saveConfig(Long userId, String jsonConfig);

    ConfigInfoVo getConfig(Long userId);

    GuidanceTableVo getTableInfo(Long userId, String tableId);

    GuidanceTableVo getTable(Long userId, String tableId);

    boolean saveTable(Long userId, String tableId, String json);

//    UploadVo upload(String url);
    UploadVo upload(MultipartFile file);

//    boolean needUpload(String imageUrl);

    List<ScoreSectionVo> listScore(String year);
}
