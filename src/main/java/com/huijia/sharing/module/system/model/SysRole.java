package com.huijia.sharing.module.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huijia.sharing.module.domain.BaseEntity;
import com.huijia.sharing.module.system.constant.UserConstants;
import com.huijia.sharing.module.system.handler.CustomLongSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



/**
 * 角色表 sys_role
 *
 * @author Lion Li
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("sys_role")
public class SysRole extends BaseEntity {

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
     * 菜单树选择项是否关联显示（ 0：父子不互相关联显示 1：父子互相关联显示）
     */
    private Boolean menuCheckStrictly;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    private Long[] menuIds;

    /**
     * 备注
     */
    private String remark;

    public SysRole(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.roleId);
    }

}
