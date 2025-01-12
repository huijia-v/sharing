package com.huijia.sharing.module.system.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.huijia.sharing.module.domain.BaseEntity;
import com.huijia.sharing.module.system.constant.UserConstants;
import com.huijia.sharing.module.system.handler.CustomLongSerializer;
import com.huijia.sharing.module.system.handler.LongArraySerializer;
import com.huijia.sharing.module.system.handler.LongToStringSerializer;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 角色信息业务对象 sys_role
 *
 * @author Michelle.Chung
 */

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRole.class, reverseConvertGenerate = false)
public class SysRoleBo extends BaseEntity {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    @Size(min = 0, max = 30, message = "角色名称长度不能超过{max}个字符")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @NotBlank(message = "角色权限字符串不能为空")
    @Size(min = 0, max = 100, message = "权限字符长度不能超过{max}个字符")
    private String roleKey;

    /**
     * 显示顺序
     */
    @NotNull(message = "显示顺序不能为空")
    private Integer roleSort;


    /**
     * 角色状态（0正常 1停用）
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 菜单组
     */
    @TableField(exist = false)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long[] menuIds;


    public SysRoleBo(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.roleId);
    }

}
