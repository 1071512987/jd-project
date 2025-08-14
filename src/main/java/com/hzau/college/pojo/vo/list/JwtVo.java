package com.hzau.college.pojo.vo.list;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 本类中存放的内容，是存在前端的token中的载荷信息
 */
@AllArgsConstructor
@Data
public class JwtVo {

    private Long id;

    private String username;

    // 角色id的集合
    private List<String> roles;//用一对多映射查询，联u,u-r,r三表，集合里只放role的name

    private String userType;

    // @AllArgsConstructor 注解会顶掉无参构造方法,因此需要补全
    public JwtVo(){};
}
