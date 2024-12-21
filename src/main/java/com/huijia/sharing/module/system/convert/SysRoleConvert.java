package com.huijia.sharing.module.system.convert;

import com.huijia.sharing.module.system.model.SysRole;
import com.huijia.sharing.module.system.model.SysRoleBo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huijia
 * @date 2024/12/16 17:30
 */
@Mapper
public interface SysRoleConvert {
    SysRoleConvert INSTANCE = Mappers.getMapper(SysRoleConvert.class);
    SysRole convert(SysRoleBo entity);
    SysRoleBo convert(SysRole entity);
}
