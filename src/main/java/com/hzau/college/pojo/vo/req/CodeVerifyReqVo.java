package com.hzau.college.pojo.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CodeVerifyReqVo {

    @ApiModelProperty("验证流水号")
    @NotBlank(message = "验证流水号不能为空")
    private String lot_number;

    @ApiModelProperty("验证输出信息")
    @NotBlank(message = "验证输出信息不能为空")
    private String captcha_output;

    @ApiModelProperty("验证通过标识")
    @NotBlank(message = "验证通过标识不能为空")
    private String pass_token;

    @ApiModelProperty("验证通过时间戳")
    @NotBlank(message = "验证通过时间戳不能为空")
    private String gen_time;

    @ApiModelProperty("验证id")
    @NotBlank(message = "验证id不能为空")
    private String captcha_id;

}
