package com.hzau.college.common.util;

import com.hzau.college.common.exception.CommonException;
import com.hzau.college.common.exception.FrontException;
import com.hzau.college.pojo.result.CodeMsg;
import com.hzau.college.pojo.vo.DataJsonVo;
import com.hzau.college.pojo.vo.JsonVo;
import com.hzau.college.pojo.vo.PageJsonVo;
import com.hzau.college.pojo.vo.PageVo;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

/**
 * 工具类，用于处理返回给客户端的Json数据
 * 包含：错误处理、正确处理、抛异常、其他方法
 *
 */

public class JsonVos {

    //错误处理<1 接受错误信息，返回给客户端JSON
    public static JsonVo error(String msg){
        return new JsonVo(false, msg);
    }

    //错误处理<2 直接返回默认的错误信息
    public static JsonVo error() {return new JsonVo(false); }

    // 错误处理<3 传入一个code和msg
    public static JsonVo error(Integer code, String msg){
        return new JsonVo(code, msg);
    }

    // 错误处理<4 传入一个codeMsg
    public static JsonVo error(CodeMsg codeMsg){
        return new JsonVo(codeMsg);
    }


    //todo 错误处理<5 接受一个Throwable参数，判断是否为我们自己的异常处理类，再返回给客户端足量的数据
//    public static JsonVo error(Throwable t){
//        if (t instanceof CommonException){
//            CommonException e = (CommonException) t;
//            return new JsonVo(e.getCode(), e.getMessage());
//        } else if (t instanceof BindException){ // 注意是validate包下的
//            // 说明是后端校验(模型参数)出错，将错误信息返回给客户单
//            BindException be = (BindException) t;
//            List<ObjectError> errors = be.getBindingResult().getAllErrors();
//            // 拿出defaultMessage信息： <1 通过遍历 <2 通过函数式编程：stream
//           List<String> defaultMsgs =  errors.stream().map((e) -> {
//                return e.getDefaultMessage();
//            }).collect(Collectors.toList());//转字符串为List，以
//            // 拼接所有的错误信息
//            String msg = StringUtils.collectionToDelimitedString(defaultMsgs, ", ");
//            return error(msg);
//        }else if (t instanceof ConstraintViolationException){
//            // 说明是后端校验(非模型参数)出错
//            ConstraintViolationException cve = (ConstraintViolationException) t;
//            List<String> defaultMsgs = cve.getConstraintViolations()
//                    .stream().map(ConstraintViolation::getMessage)
//                    .collect(Collectors.toList());
//            String msg = StringUtils.collectionToDelimitedString(defaultMsgs, ", ");
//            return error(msg);
//        }else {
//            //其他异常，直接把错误信息发给客户端
////            return error(t.getMessage());
//            return error();
//        }
//    }

    //将MybatisPlus的PageVo变为JsonVo、DataJsonVo、PageJsonVo三者之一，此处变为PageJsonVo
    public static <T> PageJsonVo<T> ok(PageVo<T> pageVo) {
        PageJsonVo<T> pageJsonVo = new PageJsonVo<>();
        pageJsonVo.setCount(pageVo.getCount());
        pageJsonVo.setData(pageVo.getData());
        //总页数暂时不要，以后可能要
        return pageJsonVo;
    }

    //将SpringData——PageVo变为MybatisPlus——PageJsonVo
    public static <T> PageJsonVo<T> ok(Page<T> page) {
        PageJsonVo<T> pageJsonVo = new PageJsonVo<>();
        pageJsonVo.setCount(page.getTotalElements());
        pageJsonVo.setData(page.getContent());
        //总页数暂时不要，以后可能要
        return pageJsonVo;
    }


    //正确处理 传入一个对象
    public  static <T> DataJsonVo<T> ok(T data){
        //HashMap的内置方法，默认返回value值而不是R本身，不可链式调用因此优先处理
        return  new DataJsonVo<>(data);
    }

    //正确处理 传入一个对象和token
    public  static <T> DataJsonVo<T> ok(T data, String token){
        //HashMap的内置方法，默认返回value值而不是R本身，不可链式调用因此优先处理
        return  new DataJsonVo<>(data, token);
    }

    //正确处理 传入一个CodeMsg
    public  static JsonVo ok(CodeMsg codeMsg){
        return new JsonVo(codeMsg);
    }

    //正确处理 传入一个CodeMsg + Token
    public  static JsonVo ok(CodeMsg codeMsg, String token){
        return new JsonVo(codeMsg, token);
    }

    //正确处理 传入一个msg
    public  static JsonVo ok(boolean ok,String msg){
        return new JsonVo(ok, msg);
    }

    //正确处理 某些情况只需要传入一个Code
     public  static JsonVo ok(){
        return  new JsonVo();
    }

    //直接抛一个异常，传msg
    //返回值是什么，我就泛型什么。泛型无意义，仅仅是为了编译不报错！
    public static <T> T raise(String msg) throws CommonException{
        throw new CommonException(msg);
    }

    //直接抛一个异常，传CodeMsg。返回值泛型：确保编译通过
    public static <T> T raise(CodeMsg codeMsg) throws  CommonException{
        throw new CommonException(codeMsg);
    }

    /**
     *     客户端错误：
     */

    //直接抛一个异常，传msg
    public static <T> T raise(boolean isServerError, String msg) throws CommonException{
        if (isServerError) throw new CommonException(msg);
        throw new FrontException(msg);
    }

    //直接抛一个异常，传msg
    public static <T> T raise(boolean isServerError, CodeMsg codeMsg) throws CommonException{
        if (isServerError) throw new CommonException(codeMsg);
        throw new FrontException(codeMsg);
    }


    //todo 返回空数据的PageVo  ---> 等待抽离到其他类中
    public static <T> PageVo<T>  getNullPage(){
        PageVo<T> res = new PageVo<>();
        Long defaultValue = 0L;
        res.setCount(defaultValue);
        res.setPages(defaultValue);
        res.setData(new ArrayList<>());
        return res;
    }


}


