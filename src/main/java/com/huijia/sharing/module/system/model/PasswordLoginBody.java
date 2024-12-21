package com.huijia.sharing.module.system.model;

//import jakarta.validation.constraints.NotBlank;
import com.huijia.sharing.core.xss.Xss;
import lombok.Data;
import lombok.EqualsAndHashCode;
//import org.dromara.common.core.xss.Xss;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static com.huijia.sharing.module.system.constant.UserConstants.*;

//import static org.dromara.common.core.constant.UserConstants.*;

/**
 * 密码登录对象
 *
 * @author Lion Li
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordLoginBody extends LoginBody {

    /**
     * 用户名
     */
    @Xss(message = "用户账号不能包含脚本字段")
    @NotBlank(message = "{user.username.not.blank}")
    @Length(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = "{user.username.length.valid}")
    private String username;

    /**
     * 用户密码
     */
    @NotBlank(message = "{user.password.not.blank}")
    @Length(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = "{user.password.length.valid}")
    private String password;

}
