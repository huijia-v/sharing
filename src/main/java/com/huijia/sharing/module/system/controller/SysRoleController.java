package com.huijia.sharing.module.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huijia.sharing.core.util.AjaxJson;
import com.huijia.sharing.module.system.annotation.Log;
import com.huijia.sharing.module.system.enums.BusinessType;
import com.huijia.sharing.module.system.model.*;
import com.huijia.sharing.module.system.model.request.ChangeRoleRequest;
import com.huijia.sharing.module.system.page.PageQuery;
import com.huijia.sharing.module.system.page.TableDataInfo;
import com.huijia.sharing.module.system.service.ISysRoleService;
import com.huijia.sharing.module.system.service.ISysUserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色信息
 *
 * @author huijia
 */
@Api(tags = "角色信息管理")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/role")
public class SysRoleController {
    private final ISysRoleService roleService;
    private final ISysUserService userService;

    /**
     * 获取角色信息列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    public AjaxJson<TableDataInfo<SysRoleVo>> list(SysRoleBo role, PageQuery pageQuery) {
        return AjaxJson.getSuccessData(roleService.selectPageRoleList(role, pageQuery));
    }

//    /**
//     * 导出角色信息列表
//     */
//    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
//    @SaCheckPermission("system:role:export")
//    @PostMapping("/export")
//    public void export(SysRole role, HttpServletResponse response) {
//        List<SysRole> list = roleService.selectRoleList(role);
//        ExcelUtil.exportExcel(list, "角色数据", SysRole.class, response);
//    }

    /**
     * 根据角色编号获取详细信息
     *
     * @param roleId 角色ID
     */
    @SaCheckPermission("system:role:query")
    @GetMapping(value = "/{roleId}")
    public AjaxJson<SysRoleVo> getInfo(@PathVariable Long roleId) {
        roleService.checkRoleDataScope(roleId);
        return AjaxJson.getSuccessData(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @SaCheckPermission("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxJson<?> add(@Validated @RequestBody SysRoleBo role) {
        roleService.checkRoleAllowed(role);
        if (!roleService.checkRoleNameUnique(role)) {
            return AjaxJson.getError("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return AjaxJson.getError("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        return AjaxJson.getSuccessData(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxJson<?> edit(@Validated @RequestBody SysRoleBo role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        if (!roleService.checkRoleNameUnique(role)) {
            return AjaxJson.getError("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return AjaxJson.getError("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }

        if (roleService.updateRole(role) > 0) {
            roleService.cleanOnlineUserByRole(role.getRoleId());
            return AjaxJson.getSuccess();
        }
        return AjaxJson.getError("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 修改保存数据权限
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    public AjaxJson<?> dataScope(@RequestBody SysRoleBo role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        return AjaxJson.getSuccessData(roleService.authDataScope(role));
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxJson<?> changeStatus(@RequestBody SysRoleBo role) {
        roleService.checkRoleAllowed(role);
        roleService.checkRoleDataScope(role.getRoleId());
        return AjaxJson.getSuccessData(roleService.updateRoleStatus(role.getRoleId(), role.getStatus()));
    }

    /**
     * 删除角色
     *
     * @param roleIds 角色ID串
     */
    @SaCheckPermission("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    public AjaxJson<?> remove(@PathVariable Long[] roleIds) {
        return AjaxJson.getSuccessData(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    @SaCheckPermission("system:role:query")
    @GetMapping("/optionselect")
    public AjaxJson<List<SysRoleVo>> optionselect() {
        return AjaxJson.getSuccessData(roleService.selectRoleAll());
    }

    /**
     * 查询已分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/allocatedList")
    public AjaxJson<TableDataInfo<SysUserVo>> allocatedList(SysUserBo user, PageQuery pageQuery) {
        return AjaxJson.getSuccessData(userService.selectAllocatedList(user, pageQuery));
    }

    /**
     * 查询未分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/unallocatedList")
    public AjaxJson<TableDataInfo<SysUserVo>> unallocatedList(SysUserBo user, PageQuery pageQuery) {
        return AjaxJson.getSuccessData(userService.selectUnallocatedList(user, pageQuery));
    }

    /**
     * 取消授权用户
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    public AjaxJson<?> cancelAuthUser(@RequestBody SysUserRole userRole) {
        return AjaxJson.getSuccessData(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID串
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    public AjaxJson<?> cancelAuthUserAll(Long roleId, Long[] userIds) {
        return AjaxJson.getSuccessData(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     * @param changeRoleRequest 角色ID和用户ID串param changeRoleRequest
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    public AjaxJson<?> selectAuthUserAll(@RequestBody ChangeRoleRequest changeRoleRequest) {
        roleService.checkRoleDataScope(changeRoleRequest.getRoleId());
        return AjaxJson.getSuccessData(roleService.insertAuthUsers(changeRoleRequest.getRoleId(), changeRoleRequest.getUserIds()));
    }

    /**
     * 获取对应角色部门树列表
     *
     * @param roleId 角色ID
     */
//    @SaCheckPermission("system:role:list")
//    @GetMapping(value = "/deptTree/{roleId}")
//    public AjaxJson<Map<String, Object>> roleDeptTreeselect(@PathVariable("roleId") Long roleId) {
//        Map<String, Object> ajax = new HashMap<>();
//        ajax.put("checkedKeys", deptService.selectDeptListByRoleId(roleId));
//        ajax.put("depts", deptService.selectDeptTreeList(new SysDept()));
//        return AjaxJson.getSuccessData(ajax);
//    }
}
