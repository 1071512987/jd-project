package com.hzau.college.common.enhance;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hzau.college.common.util.Streams;
import com.hzau.college.pojo.vo.PageVo;
import com.hzau.college.pojo.vo.req.page.PageReqVo;

import java.util.function.Function;

/**
 * 本类继承自MP的Page类，增加了构造方法和自定义方法
 * 具体来说：
 * 1.构造方法：传入的是PageReqVo，而不是Page对象
 * 2.自定义方法：buildVo，传入Vo（T）后直接返回Vo，不需要借助MP的listPage方法返回Po再转Vo
 * 3.自定义方法：buildVo，将Po（T）转换为PageVo
 */
public class MpPage<T> extends Page<T> {
    //传入的分页参数：页面+每页大小
    private final PageReqVo reqVo;

    //构造方法
    public MpPage(PageReqVo reqVo){
        //调用父类构造方法
        super(reqVo.getPage(), reqVo.getSize());
        this.reqVo = reqVo;
    }

//    //抽取公共的Build代码——返回值泛型什么，其中的代码就泛型什么
//    private <N> PageVo<N> commonBuildVo(){
//        reqVo.setPage(getCurrent());
//        reqVo.setSize(getSize());
//
//        PageVo<N> pageVo = new PageVo<>();
//        pageVo.setCount(getTotal());
//        pageVo.setPages(getPages());
//        return pageVo;
//    }


    /*
        调用此方法前，MpPage类已被更新（已执行baseMapper相关查询方法），records、current等参数已更新
        默认——自定义方法，传入Vo（T）后直接返回Vo，不需要借助MP的listPage方法返回Po再转Vo
     */
    public PageVo<T> buildVo() {
        // MP默认会对大于当前页的Page进行纠正（MP中叫做current：当前页码），因此我们最好重新 赋值
        // 更新page和size
        reqVo.setPage(getCurrent());
        reqVo.setSize(getSize());

        PageVo<T> pageVo = new PageVo<>();
        pageVo.setCount(getTotal());
        pageVo.setPages(getPages());
        // PageVo<T> pageVo = commonBuildVo();
        pageVo.setData(getRecords()); // 父类——MP中Page的方法：返回的是Vo（T），不用转直接放
        return pageVo;
    }

    // 将Po（T）转换为PageVo
    public <R> PageVo<R> buildVo(Function<T, R> function){
        // MP默认会对大于当前页的Page进行纠正（MP中叫做current：当前页码），因此我们最好重新赋值
        // 更新page和size
        reqVo.setPage(getCurrent());
        reqVo.setSize(getSize());

        PageVo<R> pageVo = new PageVo<>();
        pageVo.setCount(getTotal()); // 父类——MP中Page的方法
        pageVo.setPages(getPages());
        pageVo.setData(Streams.map(getRecords(), function)); // 父类——MP中Page的方法，返回po（T），需要转为PageVo
        return pageVo;
    }
}
