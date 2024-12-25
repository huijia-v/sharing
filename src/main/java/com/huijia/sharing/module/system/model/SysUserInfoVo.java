package com.huijia.sharing.module.system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.huijia.sharing.module.system.handler.CustomLongSerializer;
import com.huijia.sharing.module.system.handler.LongToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * 用户信息
 *
 * @author Michelle.Chung
 */
@Data
public class SysUserInfoVo {

    /**
     * 用户信息
     */
    private SysUserVo user;

    /**
     * 角色ID列表
     */
    @JsonSerialize(contentUsing = LongToStringSerializer.class)
    private List<Long> roleIds;

    /**
     * 角色列表
     */
    private List<SysRoleVo> roles;




}
