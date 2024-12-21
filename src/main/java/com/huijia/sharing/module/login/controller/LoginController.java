package com.huijia.sharing.module.login.controller;import cn.dev33.satoken.context.SaHolder;import cn.dev33.satoken.secure.BCrypt;import cn.dev33.satoken.stp.SaTokenInfo;import cn.dev33.satoken.stp.StpUtil;import cn.hutool.crypto.SecureUtil;import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;import com.github.xiaoymin.knife4j.annotations.ApiSort;import com.huijia.sharing.module.login.service.LoginService;import com.huijia.sharing.module.system.dto.RoleDTO;import com.huijia.sharing.module.system.model.LoginUser;import com.huijia.sharing.module.system.model.SysUserVo;import com.huijia.sharing.module.system.service.ISysRoleService;import com.huijia.sharing.module.system.service.ISysUserService;import com.huijia.sharing.module.system.utils.MapstructUtils;import dev.samstevens.totp.exceptions.QrGenerationException;import com.huijia.sharing.core.util.AjaxJson;import com.huijia.sharing.module.config.model.dto.SystemConfigDTO;import com.huijia.sharing.module.config.service.SystemConfigService;import com.huijia.sharing.module.login.model.enums.LoginVerifyModeEnum;import com.huijia.sharing.module.login.model.request.VerifyLoginTwoFactorAuthenticatorRequest;import com.huijia.sharing.module.login.model.result.LoginTwoFactorAuthenticatorResult;import com.huijia.sharing.module.login.model.result.LoginVerifyImgResult;import com.huijia.sharing.module.login.request.UserLoginRequest;import com.huijia.sharing.module.login.service.ImgVerifyCodeService;import com.huijia.sharing.module.login.service.TwoFactorAuthenticatorVerifyService;import io.swagger.annotations.Api;import io.swagger.annotations.ApiOperation;import org.springframework.web.bind.annotation.*;import javax.annotation.Resource;import javax.validation.Valid;import java.util.Objects;import static com.huijia.sharing.module.system.utils.LoginHelper.LOGIN_USER_KEY;/** * 登陆注销相关接口 * * @author zhaojun */@Api(tags = "登录模块")@ApiSort(1)@RestController@RequestMapping("/admin")public class LoginController {    @Resource    private SystemConfigService systemConfigService;    @Resource    private ImgVerifyCodeService imgVerifyCodeService;    @Resource    LoginService loginService;    @Resource    private TwoFactorAuthenticatorVerifyService twoFactorAuthenticatorVerifyService;    @ApiOperationSupport(order = 1, ignoreParameters = {"sharing-token"})    @ApiOperation(value = "登录")    @PostMapping("/login")    public AjaxJson<?> doLogin(@Valid @RequestBody UserLoginRequest userLoginRequest) {        String token = loginService.doLogin(userLoginRequest);        return Objects.isNull(token) ? AjaxJson.getError("登录失败, 账号或密码错误") : AjaxJson.getSuccess("登录成功！", token);    }    @ApiOperationSupport(order = 2)    @ApiOperation(value = "注销")    @PostMapping("/logout")    public AjaxJson<?> logout() {        StpUtil.logout();        return AjaxJson.getSuccess("注销成功");    }    @ApiOperationSupport(order = 3)    @ApiOperation(value = "生成 2FA")    @GetMapping("/2fa/setup")    public AjaxJson<LoginTwoFactorAuthenticatorResult> setupDevice() throws QrGenerationException {        LoginTwoFactorAuthenticatorResult loginTwoFactorAuthenticatorResult = twoFactorAuthenticatorVerifyService.setupDevice();        return AjaxJson.getSuccessData(loginTwoFactorAuthenticatorResult);    }    @ApiOperationSupport(order = 4)    @ApiOperation(value = "2FA 验证并绑定")    @PostMapping("/2fa/verify")    public AjaxJson<?> deviceVerify(@Valid @RequestBody VerifyLoginTwoFactorAuthenticatorRequest verifyLoginTwoFactorAuthenticatorRequest) {        twoFactorAuthenticatorVerifyService.deviceVerify(verifyLoginTwoFactorAuthenticatorRequest);        return AjaxJson.getSuccess();    }    @ApiOperationSupport(order = 5)    @ApiOperation(value = "获取登陆验证方式")    @GetMapping("/login/verify-mode")    public AjaxJson<LoginVerifyModeEnum> loginVerifyMode() {        SystemConfigDTO systemConfig = systemConfigService.getSystemConfig();        return AjaxJson.getSuccessData(systemConfig.getLoginVerifyMode());    }    @ApiOperationSupport(order = 6)    @ApiOperation(value = "获取图形验证码")    @GetMapping("/login/captcha")    public AjaxJson<LoginVerifyImgResult> captcha() {        LoginVerifyImgResult loginVerifyImgResult = imgVerifyCodeService.generatorCaptcha();        return AjaxJson.getSuccessData(loginVerifyImgResult);    }    @ApiOperationSupport(order = 7)    @ApiOperation(value = "检测是否已登录")    @GetMapping("/login/check")    public AjaxJson<Boolean> checkLogin() {        return AjaxJson.getSuccessData(StpUtil.isLogin());    }}