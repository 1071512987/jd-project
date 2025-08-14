package com.hzau.college.pojo.vo.list;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PostVo {
    
    private Integer id;
    @ApiModelProperty("标题")
    private String postTitle;

    @ApiModelProperty("正文")
    private String postContent;

    @ApiModelProperty("摘录")
    private String postExcerpt;

    @ApiModelProperty("作者【默认为满意志愿官方】")
    private String author;
    
    private Date createTime;
    
    private Date updateTime;

    @ApiModelProperty("是否为公告，1表示是，2表示不是，默认为2")
    private Short enInform;

    @ApiModelProperty("文章类型【非公告】【默认为综合】")
    private String postType;

    @ApiModelProperty("当前图片image对应的json")
    private String imageUris;
}

