package com.huijia.sharing.module.system.model;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户登录对象
 *
 * @author Lion Li
 */

@Data
public class LoginBody implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;

}
