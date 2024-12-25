package com.huijia.sharing.module.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.huijia.sharing.module.system.constant.UserConstants;
import com.huijia.sharing.module.system.model.entity.SysDictData;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * 字典表 数据层
 *
 * @author Lion Li
 */
@Mapper
public interface SysDictDataMapper extends BaseMapperPlus<SysDictData, SysDictData> {

    default List<SysDictData> selectDictDataByType(String dictType) {
        return selectList(
                new LambdaQueryWrapper<SysDictData>()
                        .eq(SysDictData::getStatus, UserConstants.DICT_NORMAL)
                        .eq(SysDictData::getDictType, dictType)
                        .orderByAsc(SysDictData::getDictSort));
    }
}
