package com.huijia.sharing.module.login.request;

import com.huijia.sharing.module.domain.BaseEntity;
import com.huijia.sharing.module.system.model.SysUser;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author huijia
 * @date 2024/12/27 9:28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AutoMapper(target = SysUser.class)
public class UserRegisterRequest extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
}
