package com.huijia.sharing.module.system.controller;

/**
 * @author huijia
 * @date 2024/12/20 10:38
 */

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.huijia.sharing.core.util.AjaxJson;
import com.huijia.sharing.module.config.service.SystemConfigService;
import com.huijia.sharing.module.storage.context.StorageSourceContext;
import com.huijia.sharing.module.storage.service.base.AbstractBaseFileService;
import com.huijia.sharing.module.storage.service.base.AbstractProxyTransferService;
import com.huijia.sharing.module.system.annotation.Log;
import com.huijia.sharing.module.system.enums.BusinessType;
import com.huijia.sharing.module.system.model.*;
import com.huijia.sharing.module.system.service.ISysUserService;
import com.huijia.sharing.module.system.utils.LoginHelper;
import com.huijia.sharing.module.system.utils.MimeTypeUtils;
import com.huijia.sharing.module.system.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;

/**
 * 个人中心
 *
 * @author huijia
 */
@Api(tags = "个人中心管理")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/profile")
@Slf4j
public class SysUserProfileController {
    @Resource
    private StorageSourceContext storageSourceContext;
    private final ISysUserService userService;
    @Value("${app.avatar.upload-dir}")
    private String uploadDir;
    @Resource
    private SystemConfigService systemConfigService;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取profile信息")
    @GetMapping
    public AjaxJson<?> getInfo() {
        ProfileVo profileVo = new ProfileVo();
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (ObjectUtil.isNull(loginUser)) {
            return AjaxJson.getError("无权限");
        }
        SysUserVo user = userService.selectUserById(loginUser.getUserId());
        profileVo.setUser(user);
        profileVo.setRoleGroup(userService.selectUserRoleGroup(user.getUserName()));
        return AjaxJson.getSuccessData(profileVo);
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxJson<?> updateProfile(@RequestBody SysUserProfileBo profile) {
        SysUserBo user = BeanUtil.toBean(profile, SysUserBo.class);
        if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return AjaxJson.getError("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return AjaxJson.getError("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUserId(LoginHelper.getUserId());
        if (userService.updateUserProfile(user) > 0) {
            return AjaxJson.getSuccess();
        }
        return AjaxJson.getError("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     *
     * @param bo 新旧密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxJson<?> updatePwd(@Validated @RequestBody SysUserPasswordBo bo) {
        SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
        String password = user.getPassword();
        if (!BCrypt.checkpw(bo.getOldPassword(), password)) {
            return AjaxJson.getError("修改密码失败，旧密码错误");
        }
        if (BCrypt.checkpw(bo.getNewPassword(), password)) {
            return AjaxJson.getError("新密码不能与旧密码相同");
        }

        if (userService.resetUserPwd(user.getUserId(), BCrypt.hashpw(bo.getNewPassword())) > 0) {
            return AjaxJson.getSuccess();
        }
        return AjaxJson.getError("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     *
     * @param avatarfile 用户头像
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AjaxJson<?> avatar(@RequestPart("avatarfile") MultipartFile avatarfile) {

        if (!avatarfile.isEmpty()) {
            String extension = FileUtil.extName(avatarfile.getOriginalFilename());
            if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
                return AjaxJson.getError("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
            }
        } else {
            return AjaxJson.getError("上传文件不能为空");
        }
        AbstractBaseFileService<?> fileService = storageSourceContext.getByStorageKey("avatar");
        if (ObjectUtil.isNull(fileService)) {
            return AjaxJson.getError("需要创建一个名为【avatar】的存储源，才能上传头像");
        }
        String uploadUrl = fileService.getUploadUrl("/avatar",
                avatarfile.getOriginalFilename(), avatarfile.getSize());
        if (StringUtils.isNotEmpty(uploadUrl)) {
            try {
                AbstractProxyTransferService<?> avatar = (AbstractProxyTransferService<?>) storageSourceContext.getByStorageKey("avatar");
                avatar.uploadFile("avatar/" + avatarfile.getOriginalFilename(), avatarfile.getInputStream());
                LoginUser loginUser = LoginHelper.getLoginUser();
                if (ObjectUtil.isNull(loginUser)) {
                    return AjaxJson.getError("无权限");
                }
                uploadUrl = avatar.getDownloadUrl("/avatar/" + avatarfile.getOriginalFilename());
                if (userService.updateUserAvatar(loginUser.getUserId(), uploadUrl)) {
                    AvatarVo avatarVo = new AvatarVo();
                    avatarVo.setImgUrl(uploadUrl);
                    return AjaxJson.getSuccessData(avatarVo);
                }
            } catch (IOException e) {
                log.error("Failed to upload the avatar: {}", e.getMessage());
                return AjaxJson.getError("上传图片异常，请联系管理员");
            }
        }
        return AjaxJson.getError("上传图片异常，请联系管理员");
    }
}
