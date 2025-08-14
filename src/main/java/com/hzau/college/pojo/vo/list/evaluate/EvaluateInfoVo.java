package com.hzau.college.pojo.vo.list.evaluate;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放评测具体信息的Vo")
public class EvaluateInfoVo {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("评测标题")
    private String title;

    @ApiModelProperty("评测名称")
    private String name;

    @ApiModelProperty("列表简介")
    private String listIntro;

//    @ApiModelProperty("详情页简介")
//    private String detailIntro;
//
//    @ApiModelProperty("作答页简介")
//    private String answerIntro;

    @ApiModelProperty("题目数量")
    private Integer questionNum;

    @ApiModelProperty("预计作答时间【分钟】")
    private Integer estimatedTime;

    @ApiModelProperty("已完成人数")
    private Integer completedTotal;
}
