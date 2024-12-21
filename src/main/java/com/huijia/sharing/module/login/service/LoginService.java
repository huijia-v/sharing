package com.huijia.sharing.module.login.service;

import com.huijia.sharing.module.login.request.UserLoginRequest;
import com.huijia.sharing.module.system.model.LoginUser;

import java.util.List;

/**
 * @author huijia
 * @date 2024/12/19 18:00
 */
public interface LoginService {
    String doLogin(UserLoginRequest userLoginRequest);

    List<Long> getRoles(Long userId);

}
