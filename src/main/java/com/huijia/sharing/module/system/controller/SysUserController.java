package com.huijia.sharing.module.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.huijia.sharing.core.util.AjaxJson;
import com.huijia.sharing.module.system.annotation.Log;
import com.huijia.sharing.module.system.constant.UserConstants;
import com.huijia.sharing.module.system.enums.BusinessType;
import com.huijia.sharing.module.system.model.*;
import com.huijia.sharing.module.system.page.PageQuery;
import com.huijia.sharing.module.system.page.TableDataInfo;
import com.huijia.sharing.module.system.service.ISysRoleService;
import com.huijia.sharing.module.system.service.ISysUserService;
import com.huijia.sharing.module.system.utils.LoginHelper;
import com.huijia.sharing.module.system.utils.StreamUtils;
import com.huijia.sharing.module.system.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息
 *
 * @author Lion Li
 */
@Api(tags = "用户管理模块")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/user")
public class SysUserController {
    private final ISysUserService userService;
    private final ISysRoleService roleService;

    /**
     * 获取用户列表
     */
    @ApiOperation(value = "获取用户列表")
    @GetMapping("/list")
    public AjaxJson<TableDataInfo<SysUserVo>> list(SysUserBo user, PageQuery pageQuery) {
        return AjaxJson.getSuccessData(userService.selectPageUserList(user, pageQuery));
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getInfo")
    public AjaxJson<?> getInfo() {
        UserInfoVo userInfoVo = new UserInfoVo();
        LoginUser loginUser = LoginHelper.getLoginUser();
        SysUserVo user = userService.selectUserById(loginUser.getUserId());
        if (ObjectUtil.isNull(user)) {
            return AjaxJson.getError("没有用户数据!");
        }
        userInfoVo.setUser(user);
        userInfoVo.setRoles(loginUser.getRolePermission());
        return AjaxJson.getSuccessData(userInfoVo);
    }

    /**
     * 根据用户编号获取详细信息
     *
     * @param userId 用户ID
     */
    @ApiOperation(value = "根据用户编号获取详细信息")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxJson<SysUserInfoVo> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        userService.checkUserDataScope(userId);
        SysUserInfoVo userInfoVo = new SysUserInfoVo();
        SysRoleBo roleBo = new SysRoleBo();
        roleBo.setStatus(UserConstants.ROLE_NORMAL);
        List<SysRoleVo> roles = roleService.selectRoleList(roleBo);
        userInfoVo.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
        if (ObjectUtil.isNotNull(userId)) {
            SysUserVo sysUser = userService.selectUserById(userId);
            userInfoVo.setUser(sysUser);
            userInfoVo.setRoleIds(StreamUtils.toList(sysUser.getRoles(), SysRoleVo::getRoleId));
        }
        return AjaxJson.getSuccessData(userInfoVo);
    }

    /**
     * 新增用户
     */
    @ApiOperation(value = "新增用户")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxJson<?> add(@Validated @RequestBody SysUserBo user) {
        if (!userService.checkUserNameUnique(user)) {
            return AjaxJson.getError("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return AjaxJson.getError("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return AjaxJson.getError("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return AjaxJson.getSuccessData(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @ApiOperation(value = "修改用户")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxJson<?> edit(@Validated @RequestBody SysUserBo user) {
        userService.checkUserAllowed(user.getUserId());
        userService.checkUserDataScope(user.getUserId());
        if (!userService.checkUserNameUnique(user)) {
            return AjaxJson.getError("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return AjaxJson.getError("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return AjaxJson.getError("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return AjaxJson.getSuccess("修改成功！", userService.updateUser(user));
    }

    /**
     * 删除用户
     *
     * @param userIds 角色ID串
     */
    @ApiOperation(value = "删除用户")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxJson<?> remove(@PathVariable Long[] userIds) {
        if (ArrayUtil.contains(userIds, LoginHelper.getUserId())) {
            return AjaxJson.getError("当前用户不能删除");
        }
        return AjaxJson.getSuccessData(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @ApiOperation(value = "重置密码")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxJson<?> resetPwd(@RequestBody SysUserBo user) {
        userService.checkUserAllowed(user.getUserId());
        userService.checkUserDataScope(user.getUserId());
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return AjaxJson.getSuccessData(userService.resetUserPwd(user.getUserId(), user.getPassword()));
    }

    /**
     * 状态修改
     */
    @ApiOperation(value = "状态修改")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxJson<?> changeStatus(@RequestBody SysUserBo user) {
        userService.checkUserAllowed(user.getUserId());
        userService.checkUserDataScope(user.getUserId());
        return AjaxJson.getSuccessData(userService.updateUserStatus(user.getUserId(), user.getStatus()));
    }

    /**
     * 根据用户编号获取授权角色
     *
     * @param userId 用户ID
     */
    @ApiOperation(value = "根据用户编号获取详细信息")
    @GetMapping("/authRole/{userId}")
    public AjaxJson<?> authRole(@PathVariable Long userId) {
        SysUserVo user = userService.selectUserById(userId);
        List<SysRoleVo> roles = roleService.selectRolesByUserId(userId);
        SysUserInfoVo userInfoVo = new SysUserInfoVo();
        userInfoVo.setUser(user);
        userInfoVo.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
        return AjaxJson.getSuccessData(userInfoVo);
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户Id
     * @param roleIds 角色ID串
     */
    @ApiOperation(value = "用户授权角色")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxJson<Void> insertAuthRole(Long userId, Long[] roleIds) {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return AjaxJson.getSuccess();
    }
}
