package com.huijia.sharing.module.system.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huijia.sharing.module.system.handler.CustomLongSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 角色表 sys_role
 *
 * @author Lion Li
 */

@Data
@NoArgsConstructor
@TableName("sys_role")
public class SysRole {

    /**
     * 角色ID
     */
    @TableId(value = "role_id")
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限
     */
    private String roleKey;

    /**
     * 角色排序
     */
    private Integer roleSort;


    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 备注
     */
    private String remark;

    public SysRole(Long roleId) {
        this.roleId = roleId;
    }

}
