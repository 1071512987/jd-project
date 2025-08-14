package com.hzau.college.pojo.vo.list;

import lombok.Data;

@Data
public class GeeResBody {

    private String result;

    private String reason;

    // dict：嵌套json
    private String captchaArgs;

}
