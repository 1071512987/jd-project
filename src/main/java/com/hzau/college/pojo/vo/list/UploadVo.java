package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author 半夜里咳嗽的狼
 * @Date 2023/3/30 15:03
 * @Description: TODO
 */
@Data
public class UploadVo {

   @ApiModelProperty("图片上传后URL")
   private String url;
}
