package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放最小div的院校数据【如用于相似院校信息的展示】")
public class CollegeVo {

    // logo

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("院校名称")
    private String name;

    @ApiModelProperty("院校层次")
    private String levelName;

    @ApiModelProperty("办学形式【如公办】")
    private String organizationName;

    @ApiModelProperty("院校所有标签")
    private String allLabel;

    @ApiModelProperty("院校类型名称【如理工类】")
    private String typeName;

    @ApiModelProperty("隶属机构")
    private String department;

    @ApiModelProperty("所在省份")
    private String provinceName;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("所在区县")
    private String countyName;
}
