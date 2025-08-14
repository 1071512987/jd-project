package com.hzau.college.common.exception;

import com.hzau.college.common.util.Debugs;
import com.hzau.college.common.util.JsonVos;
import com.hzau.college.pojo.vo.JsonVo;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 【兜底的】：常规异常拦截处理器
 * &#064;Order注解  指定实体Bean被加载到Spring容器的顺序，注解中的值越小越优先加载
 * 此处@Order值仅对相同类型的异常处理器有效，不同类型的异常处理器之间的顺序不受@Order影响，
 * 捕捉方法选择原则是<1类型匹配、<2声明顺序、<3最近匹配原则（子类大于父类） ，并且遍历所有的异常处理器的所有方法后在决定选择哪一个处理器
 *
 * 假设你在 ServiceImpl 层调用了 JsonVos.raise(false, CodeMsg.TOKEN_FORMAT_ERROR) 抛出了异常，
 * 而在 ServiceImpl 或 Controller 层没有显式的 try-catch 捕获该异常。
 * 此时，异常将沿着调用栈向上抛出，直到被 Spring 框架捕获为止。
 * 如果你使用了 @RestControllerAdvice，它会处理异常并返回适当的响应。
 */
@Order(10)
@RestControllerAdvice
public class CommonExceptionHandler {

    /*
        直接返回JsonVo对象，Spring自动转换为JSON格式返回
        旧的写法
            - 返回void
            - response.setContentType(MediaType.APPLICATION_JSON_VALUE)；
            - response.setCharacterEncoding(StandardCharsets.UTF08.displayName());
            - response.getWriter().write(Rs.error(t).jsonString())
     */
    /**
     * 状态码：400
     * 抛出CommonException
     * @param t 异常
     */
    @ExceptionHandler(Throwable.class) // 意思是
    @ResponseStatus(code = HttpStatus.BAD_REQUEST) // 返回的状态码
    public JsonVo handle(Throwable t) throws Exception {
        // 调试阶段在控制台打印详细异常信息
        Debugs.run(t::printStackTrace);

        // 处理自定义的commonException
        if (t instanceof CommonException) {
            return handle((CommonException) t);
        }

        // 以下内容已整合到FrontExceptionHandler类中
//        else if (t instanceof BindException) { // 注意是validate包下的
//            // 说明是后端校验(模型参数)出错，将错误信息返回给客户单
//            return handle((BindException) t);
//        } else if (t instanceof ConstraintViolationException) {
//            // 说明是后端校验(非模型参数)出错
//            return handle((ConstraintViolationException) t);
//        }
//        else if (t instanceof AuthenticationException){
//            return JsonVos.error(CodeMsg.LACK_PERMISSION);
//        }

        //其他异常，递归查找异常链条Caused By上的所有异常，直到最终嵌套异常结果为空
        Throwable cause = t.getCause();
        if (cause != null){
          return  handle(cause);
        }

        //如果没有能够捕捉到的嵌套异常，直接抛出默认的异常（状态码400，无msg）
        return JsonVos.error();
    }

    // 处理CommonException
    private JsonVo handle(CommonException ce) {
        return JsonVos.error(ce.getCode(), ce.getMessage());
    }


}
