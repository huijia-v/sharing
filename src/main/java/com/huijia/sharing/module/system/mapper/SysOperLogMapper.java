package com.huijia.sharing.module.system.mapper;


import com.huijia.sharing.module.system.model.log.SysOperLog;
import com.huijia.sharing.module.system.model.log.SysOperLogVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志 数据层
 *
 * @author Lion Li
 */
@Mapper
public interface SysOperLogMapper extends BaseMapperPlus<SysOperLog, SysOperLogVo> {

}
