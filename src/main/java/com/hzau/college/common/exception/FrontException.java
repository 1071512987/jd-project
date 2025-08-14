package com.hzau.college.common.exception;

import com.hzau.college.pojo.result.CodeMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常类CommonException
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FrontException extends RuntimeException{
    //默认code
    private static final int CODE_DEFAULT = CodeMsg.BAD_REQUEST.getCode();

    private int code;

    //构造方法

    /**
     * 创建新的异常源头：不含cause
     */
    public FrontException(){
        this(CODE_DEFAULT,null);
    }

    public FrontException(String msg){
        this(msg,null);
    }

    public FrontException(int code, String msg){
        this(code, msg, null);
    }

    public FrontException(CodeMsg codeMsg){
        this(codeMsg,null);
    }

    /**
     *  创建嵌套异常：含有cause
     *  cause是导致异常的根本原因，在框架中异常发生后，会将此异常嵌套在更大范围异常中继续抛出
     *  cause在父类Throwable中存在
     *  我们想要使用自己的异常处理类统一处理发生的异常，但是异常的根本原因还是需要传入，也就是此处的cause
     */

    public FrontException(String msg, Throwable cause){
        this(CODE_DEFAULT, msg, cause);
    }

    //code与message拆开
    public FrontException(int code, String msg, Throwable cause){
        super(msg,cause);
        this.code = code;
    }

    //code与message合并为CodeMsg
    public FrontException(CodeMsg codeMsg, Throwable cause){
        this(codeMsg.getCode(), codeMsg.getMsg(), cause);
    }

}
