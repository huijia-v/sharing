package com.huijia.sharing.module.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.util.ObjectUtil;
import com.huijia.sharing.core.util.AjaxJson;
import com.huijia.sharing.module.system.annotation.Log;
import com.huijia.sharing.module.system.enums.BusinessType;
import com.huijia.sharing.module.system.model.entity.SysDictData;
import com.huijia.sharing.module.system.page.PageQuery;
import com.huijia.sharing.module.system.page.TableDataInfo;
import com.huijia.sharing.module.system.service.ISysDictDataService;
import com.huijia.sharing.module.system.service.ISysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/dict/data")
public class SysDictDataController {

    private final ISysDictDataService dictDataService;
    private final ISysDictTypeService dictTypeService;

    /**
     * 查询字典数据列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public AjaxJson<TableDataInfo<SysDictData>> list(SysDictData dictData, PageQuery pageQuery) {
        return AjaxJson.getSuccessData(dictDataService.selectPageDictDataList(dictData, pageQuery));
    }

    /**
     * 导出字典数据列表
     */
//    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
//    @SaCheckPermission("system:dict:export")
//    @PostMapping("/export")
//    public void export(SysDictData dictData, HttpServletResponse response) {
//        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
//        ExcelUtil.exportExcel(list, "字典数据", SysDictData.class, response);
//    }

    /**
     * 查询字典数据详细
     *
     * @param dictCode 字典code
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{dictCode}")
    public AjaxJson<SysDictData> getInfo(@PathVariable Long dictCode) {
        return AjaxJson.getSuccessData(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxJson<List<SysDictData>> dictType(@PathVariable String dictType) {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (ObjectUtil.isNull(data)) {
            data = new ArrayList<>();
        }
        return AjaxJson.getSuccessData(data);
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxJson<Void> add(@Validated @RequestBody SysDictData dict) {
        dictDataService.insertDictData(dict);
        return AjaxJson.getSuccess();
    }

    /**
     * 修改保存字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxJson<Void> edit(@Validated @RequestBody SysDictData dict) {
        dictDataService.updateDictData(dict);
        return AjaxJson.getSuccess();
    }

    /**
     * 删除字典类型
     *
     * @param dictCodes 字典code串
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictCodes}")
    public AjaxJson<Void> remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return AjaxJson.getSuccess();
    }
}
