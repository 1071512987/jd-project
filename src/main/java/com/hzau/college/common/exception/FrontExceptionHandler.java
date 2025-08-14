package com.hzau.college.common.exception;

import com.hzau.college.common.util.JsonVos;
import com.hzau.college.common.util.Streams;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.JsonVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;


/**
 * 自定义Front前台异常拦截处理器
 * &#064;Order注解  指定实体Bean被加载到Spring容器的顺序，注解中的值越小越优先加载
 * 此处@Order值仅对相同类型的异常处理器有效，不同类型的异常处理器之间的顺序不受@Order影响，
 * (相同类型的异常处理器是指那些使用相同注解（如@RestControllerAdvice）并处理Spring MVC中的异常的处理器)
 * 捕捉方法选择原则是<1类型匹配、<2声明顺序、<3最近匹配原则（子类大于父类） ，并且遍历所有的异常处理器的所有方法后在决定选择哪一个处理器
 *
 * 假设你在 ServiceImpl 层调用了 JsonVos.raise(false, CodeMsg.TOKEN_FORMAT_ERROR) 抛出了异常，
 * 而在 ServiceImpl 或 Controller 层没有显式的 try-catch 捕获该异常。
 * 此时，异常将沿着调用栈向上抛出，直到被 Spring 框架捕获为止。
 * 如果你使用了 @RestControllerAdvice，它会处理异常并返回适当的响应。
 */
@Order(1)
@Slf4j
@RestControllerAdvice
public class FrontExceptionHandler {

    /*
        处理FrontException
     */
    @ExceptionHandler(FrontException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonVo handle(FrontException t) throws Exception {
        return JsonVos.error(t.getCode(), t.getMessage());
    }

    /*
          处理后端校验(模型参数)出错
          当Spring MVC在绑定请求参数到Java对象时发生错误时抛出。
          例如，表单提交的数据无法绑定到控制器方法的参数对象上，
          通常是因为数据格式不正确或缺少必需的字段。
       */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonVo handle(BindException be) throws Exception {
        List<ObjectError> errors = be.getBindingResult().getAllErrors();
        //  通过函数式编程：stream拿出defaultMessage信息——转字符串为List，以拼接所有的错误信息
        List<String> defaultMsgs = Streams.map(errors, ObjectError::getDefaultMessage);
        // 将collection中所有对象用某个符号进行拼接
        String msg = StringUtils.collectionToDelimitedString(defaultMsgs, ", ");
        return JsonVos.error(CodeMsg.VALIDATE_ERROR.getCode(), msg);
    }

    /*
        处理后端校验(非模型参数)出错
        当使用Java Bean Validation（JSR 380）进行参数验证时抛出。
        例如，方法参数或字段不满足注解约束（如@NotNull、@Size等）时，会抛出此异常。
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonVo handle(ConstraintViolationException cve) throws Exception {
        List<String> defaultMsgs = Streams.map(cve.getConstraintViolations(), ConstraintViolation::getMessage);
        String msg = StringUtils.collectionToDelimitedString(defaultMsgs, ", ");
        return JsonVos.error(CodeMsg.VALIDATE_ERROR.getCode(), msg);
    }

    /*
        处理shiro【鉴权】相关的兜底异常 AuthorizationException
        注意：验证相关的异常处理在UserServiceImpl处理完毕！
     */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.OK)
    public JsonVo handle(AuthorizationException ae) throws Exception {
//        ae.printStackTrace();
        return JsonVos.error(CodeMsg.LACK_PERMISSION);
    }

}
