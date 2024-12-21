package com.huijia.sharing.module.login.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.huijia.sharing.module.config.model.dto.SystemConfigDTO;
import com.huijia.sharing.module.config.service.SystemConfigService;
import com.huijia.sharing.module.login.model.enums.LoginVerifyModeEnum;
import com.huijia.sharing.module.login.request.UserLoginRequest;
import com.huijia.sharing.module.system.dto.RoleDTO;
import com.huijia.sharing.module.system.model.LoginUser;
import com.huijia.sharing.module.system.model.SysUserVo;
import com.huijia.sharing.module.system.service.ISysRoleService;
import com.huijia.sharing.module.system.service.ISysUserService;
import com.huijia.sharing.module.system.utils.LoginHelper;
import com.huijia.sharing.module.system.utils.MapstructUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.huijia.sharing.module.system.utils.LoginHelper.LOGIN_USER_KEY;

/**
 * @author huijia
 * @date 2024/12/19 18:02
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Resource
    private ISysUserService userService;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private ImgVerifyCodeService imgVerifyCodeService;

    @Resource
    private TwoFactorAuthenticatorVerifyService twoFactorAuthenticatorVerifyService;

    @Resource
    private SystemConfigService systemConfigService;
    @Override
    public String doLogin(UserLoginRequest userLoginRequest) {
        SystemConfigDTO systemConfig = systemConfigService.getSystemConfig();
        String verifyCode = userLoginRequest.getVerifyCode();
        String verifyCodeUuid = userLoginRequest.getVerifyCodeUUID();

        LoginVerifyModeEnum loginVerifyMode = systemConfig.getLoginVerifyMode();
        String loginVerifySecret = systemConfig.getLoginVerifySecret();

        SysUserVo sysUserVo = userService.selectUserByUserName(userLoginRequest.getUsername());
        if (Objects.equals(loginVerifyMode, LoginVerifyModeEnum.TWO_FACTOR_AUTHENTICATION_MODE)) {
            twoFactorAuthenticatorVerifyService.checkCode(loginVerifySecret, verifyCode);
        } else if (Objects.equals(loginVerifyMode, LoginVerifyModeEnum.IMG_VERIFY_MODE)) {
            imgVerifyCodeService.checkCaptcha(verifyCodeUuid, verifyCode);
        }
        if (!Objects.isNull(sysUserVo) && BCrypt.checkpw(userLoginRequest.getPassword(), sysUserVo.getPassword())) {
            LoginUser loginUser = new LoginUser();
            loginUser.setLoginTime(System.currentTimeMillis());
            loginUser.setUsername(userLoginRequest.getUsername());
            loginUser.setUserId(sysUserVo.getUserId());
            loginUser.setUserType(sysUserVo.getUserType());
            loginUser.setRoles(MapstructUtils.convert(roleService.selectRolesByUserId(sysUserVo.getUserId()), RoleDTO.class));
            SaLoginModel saLoginModel = new SaLoginModel();
            LoginHelper.login(loginUser, saLoginModel);
            return StpUtil.getTokenValue();
        }
        return null;
    }

    @Override
    public List<Long> getRoles(Long userId) {
        return Collections.emptyList();
    }
}
