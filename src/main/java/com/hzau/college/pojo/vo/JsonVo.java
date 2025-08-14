package com.hzau.college.pojo.vo;

import com.hzau.college.pojo.result.CodeMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 返回不带结果的Json信息
 */

@ApiModel("返回结果")
@Data
public class JsonVo {

    @ApiModelProperty("代码【0代表成功，其他代表失败】")
    private  Integer code;

    @ApiModelProperty("消息描述")
    private  String msg;

    @ApiModelProperty("新token")
    private  String token = null;

//    @ApiModelProperty("IP")
//    private String host; // IP


    //正确编码，只有一个
    private static final int CODE_OK = CodeMsg.OPERATE_OK.getCode();
    //错误编码，有很多个，挑一个作为默认
    private static final int CODE_ERROR = CodeMsg.BAD_REQUEST.getCode();

    /**
     * 构造方法
     */
    public JsonVo(){
        this(true);
    }

    public JsonVo(String token){
        this(true, null, token);
    }

    public JsonVo(boolean ok){
        this(ok, null);
    }

    public JsonVo(boolean ok, String msg){
        this(ok ? CODE_OK : CODE_ERROR, msg);
    }

    public JsonVo(boolean ok, String msg, String token){
        this(ok ? CODE_OK : CODE_ERROR, msg, token);
    }

    public JsonVo(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public JsonVo(int code, String msg, String token){
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    public JsonVo(CodeMsg codeMsg){
        this(codeMsg.getCode(), codeMsg.getMsg());
    }

    public JsonVo(CodeMsg codeMsg, String token){
        this(codeMsg.getCode(), codeMsg.getMsg(), token);
    }

}
