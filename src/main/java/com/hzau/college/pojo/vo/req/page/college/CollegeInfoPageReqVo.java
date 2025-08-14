package com.hzau.college.pojo.vo.req.page.college;

import com.baomidou.mybatisplus.annotation.TableField;
import com.hzau.college.common.annotation.validator.BoolPlusNumber;
import com.hzau.college.common.annotation.validator.CollegeEducationLevel;
import com.hzau.college.common.annotation.validator.CollegeType;
import com.hzau.college.pojo.vo.req.page.KeywordPageReqVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CollegeInfoPageReqVo extends KeywordPageReqVo {

    @ApiModelProperty("教育等级【普通本科、专科（高职）】")
    @CollegeEducationLevel
    private String levelName;

    @ApiModelProperty("所在省份Id【多个id以逗号,分隔】")
    private String provinceIds;

    @ApiModelProperty("院校类型【如综合、理工、师范】【多个类型以逗号,分隔】")
    @CollegeType
    private String typeName;

    @ApiModelProperty("是否为985院校【1说明是，2说明不是】")
    @BoolPlusNumber
    @TableField("en_985")
    private String en985;

    @ApiModelProperty("是否为211院校【1说明是，2说明不是】")
    @BoolPlusNumber
    @TableField("en_211")
    private String en211;

    @ApiModelProperty("是否为双一流院校【1说明是，2说明不是】")
    @BoolPlusNumber
    private String enDualClass;

    @ApiModelProperty("办学组织【公办、民办、中外合作】")
    @CollegeType
    private String organizationName;
}

