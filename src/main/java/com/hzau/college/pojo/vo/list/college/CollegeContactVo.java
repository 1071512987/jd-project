package com.hzau.college.pojo.vo.list.college;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("存放院校信息中——联系方式的Vo")
public class CollegeContactVo {

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("官方邮箱")
    private String email;

    @ApiModelProperty("院校官网")
    private String website;
}
