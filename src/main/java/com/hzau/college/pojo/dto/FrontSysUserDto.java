package com.hzau.college.pojo.dto;

import com.hzau.college.pojo.po.front.FrontSysResource;
import com.hzau.college.pojo.po.front.FrontSysRole;
import com.hzau.college.pojo.po.front.FrontSysUser;
import lombok.Data;

import java.util.List;

/**
 * 仅在内部传输，放入缓存中获取信息
 */
@Data
public class FrontSysUserDto {
    private FrontSysUser user;
    private List<FrontSysRole> roles;
    private List<FrontSysResource> resources;
}
