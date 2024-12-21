package com.huijia.sharing.module.system.model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.huijia.sharing.module.system.constant.UserConstants;
import com.huijia.sharing.module.system.dto.RoleDTO;
import com.huijia.sharing.module.system.handler.CustomLongSerializer;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息视图对象 sys_role
 *
 * @author Michelle.Chung
 */
@Data
@AutoMapper(target = RoleDTO.class)
public class SysRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;

    /**
     * 显示顺序
     */
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户是否存在此角色标识 默认不存在
     */
    private boolean flag = false;

    public boolean isSuperAdmin() {
        return UserConstants.SUPER_ADMIN_ID.equals(this.roleId);
    }

}
