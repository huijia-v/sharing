package com.huijia.sharing.module.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.lang.tree.Tree;

import com.huijia.sharing.core.util.AjaxJson;
import com.huijia.sharing.core.util.CodeMsg;
import com.huijia.sharing.module.system.annotation.Log;
import com.huijia.sharing.module.system.constant.UserConstants;
import com.huijia.sharing.module.system.enums.BusinessType;
import com.huijia.sharing.module.system.model.entity.SysMenu;
import com.huijia.sharing.module.system.service.ISysMenuService;
import com.huijia.sharing.module.system.utils.LoginHelper;
import com.huijia.sharing.module.system.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/menu")
public class SysMenuController {

    private final ISysMenuService menuService;

    /**
     * 获取菜单列表
     */
    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    public AjaxJson<List<SysMenu>> list(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, LoginHelper.getUserId());
        return AjaxJson.getSuccessData(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     *
     * @param menuId 菜单ID
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = "/{menuId}")
    public AjaxJson<SysMenu> getInfo(@PathVariable Long menuId) {
        return AjaxJson.getSuccessData(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxJson<List<Tree<Long>>> treeselect(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, LoginHelper.getUserId());
        return AjaxJson.getSuccessData(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     *
     * @param roleId 角色ID
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxJson<Map<String, Object>> roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysMenu> menus = menuService.selectMenuList(LoginHelper.getUserId());
        Map<String, Object> ajax = new HashMap<>();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return AjaxJson.getSuccessData(ajax);
    }

    /**
     * 新增菜单
     */
    @SaCheckPermission("system:menu:add")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxJson<?> add(@Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return AjaxJson.getError("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return AjaxJson.getError("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        return AjaxJson.getSuccessData(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @SaCheckPermission("system:menu:edit")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxJson<?> edit(@Validated @RequestBody SysMenu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return AjaxJson.getError("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return AjaxJson.getError("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return AjaxJson.getError("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        return AjaxJson.getSuccessData(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
    @SaCheckPermission("system:menu:remove")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public AjaxJson<?> remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return AjaxJson.getWarning("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return AjaxJson.getWarning("菜单已分配,不允许删除");
        }
        return AjaxJson.getSuccessData(menuService.deleteMenuById(menuId));
    }
}
