package com.huijia.sharing.module.login.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huijia.sharing.core.exception.ServiceException;
import com.huijia.sharing.core.util.CodeMsg;
import com.huijia.sharing.module.config.model.dto.SystemConfigDTO;
import com.huijia.sharing.module.config.service.SystemConfigService;
import com.huijia.sharing.module.login.enums.UserStatus;
import com.huijia.sharing.module.login.model.enums.LoginVerifyModeEnum;
import com.huijia.sharing.module.login.request.UserLoginRequest;
import com.huijia.sharing.module.system.dto.RoleDTO;
import com.huijia.sharing.module.system.mapper.SysUserMapper;
import com.huijia.sharing.module.system.model.LoginUser;
import com.huijia.sharing.module.system.model.SysUser;
import com.huijia.sharing.module.system.model.SysUserVo;
import com.huijia.sharing.module.system.service.ISysRoleService;
import com.huijia.sharing.module.system.service.ISysUserService;
import com.huijia.sharing.module.system.utils.LoginHelper;
import com.huijia.sharing.module.system.utils.MapstructUtils;
import com.huijia.sharing.module.system.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.huijia.sharing.module.system.utils.LoginHelper.LOGIN_USER_KEY;

/**
 * @author huijia
 * @date 2024/12/19 18:02
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private SysUserMapper userMapper;
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

        SysUserVo sysUserVo = loadUserByUsername(userLoginRequest.getUsername());
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
            loginUser.setRoles(MapstructUtils.convert(sysUserVo.getRoles(), RoleDTO.class));
            SaLoginModel saLoginModel = new SaLoginModel();
            LoginHelper.login(loginUser, saLoginModel);
            recordLoginInfo(loginUser.getUserId(), loginUser.getUsername());
            return StpUtil.getTokenValue();
        }
        return null;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId, String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(ServletUtils.getClientIP());
        sysUser.setLoginDate(new Date());
        userMapper.updateById(sysUser);
    }


    private SysUserVo loadUserByUsername(String username) {
        SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getUserName, SysUser::getStatus)
                .eq(SysUser::getUserName, username));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("用户不存在", CodeMsg.ERROR);
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException(username + " 用户已被停用", CodeMsg.ERROR);
        }
        return userMapper.selectUserByUserName(username);
    }

    @Override
    public List<Long> getRoles(Long userId) {
        return Collections.emptyList();
    }
}
