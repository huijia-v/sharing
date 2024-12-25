package com.huijia.sharing.module.system.mapper;

import com.huijia.sharing.module.system.model.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与菜单关联表 数据层
 * @author huijia
 * @date 2024/12/24 14:19
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapperPlus<SysRoleMenu, SysRoleMenu> {

}