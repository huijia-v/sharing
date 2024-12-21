package com.huijia.sharing.module.system.convert;

import com.huijia.sharing.module.system.event.OperLogEvent;
import com.huijia.sharing.module.system.model.log.SysOperLog;
import com.huijia.sharing.module.system.model.log.SysOperLogBo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author huijia
 * @date 2024/12/16 17:33
 */
@Mapper
public interface SysOperLogConvert {
    SysOperLogConvert INSTANCE = Mappers.getMapper(SysOperLogConvert.class);

    OperLogEvent convert(SysOperLogBo bo);
    SysOperLogBo convert(OperLogEvent entity);
    SysOperLog convertToOpelog(SysOperLogBo entity);
}
