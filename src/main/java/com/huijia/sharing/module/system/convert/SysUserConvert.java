package com.huijia.sharing.module.system.convert;


import com.huijia.sharing.module.system.model.SysUser;
import com.huijia.sharing.module.system.model.SysUserBo;
import com.huijia.sharing.module.system.model.SysUserVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface SysUserConvert {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

    SysUser convert(SysUserBo entity);

    SysUserBo convert(SysUser vo);

    SysUser convert(SysUserVo vo);
}
