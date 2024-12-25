package com.huijia.sharing.module.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.huijia.sharing.core.util.AjaxJson;
import com.huijia.sharing.module.system.annotation.Log;
import com.huijia.sharing.module.system.enums.BusinessType;
import com.huijia.sharing.module.system.model.entity.SysDictType;
import com.huijia.sharing.module.system.page.PageQuery;
import com.huijia.sharing.module.system.page.TableDataInfo;
import com.huijia.sharing.module.system.service.ISysDictTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/dict/type")
public class SysDictTypeController {

    private final ISysDictTypeService dictTypeService;

    /**
     * 查询字典类型列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public AjaxJson<TableDataInfo<SysDictType>> list(SysDictType dictType, PageQuery pageQuery) {
        return AjaxJson.getSuccessData(dictTypeService.selectPageDictTypeList(dictType, pageQuery));
    }

    /**
     * 导出字典类型列表
     */
//    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
//    @SaCheckPermission("system:dict:export")
//    @PostMapping("/export")
//    public void export(SysDictType dictType, HttpServletResponse response) {
//        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
//        ExcelUtil.exportExcel(list, "字典类型", SysDictType.class, response);
//    }

    /**
     * 查询字典类型详细
     *
     * @param dictId 字典ID
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{dictId}")
    public AjaxJson<SysDictType> getInfo(@PathVariable Long dictId) {
        return AjaxJson.getSuccessData(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxJson<?> add(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return AjaxJson.getError("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dictTypeService.insertDictType(dict);
        return AjaxJson.getSuccess();
    }

    /**
     * 修改字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxJson<?> edit(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return AjaxJson.getError("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dictTypeService.updateDictType(dict);
        return AjaxJson.getSuccess();
    }

    /**
     * 删除字典类型
     *
     * @param dictIds 字典ID串
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public AjaxJson<Void> remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return AjaxJson.getSuccess();
    }

    /**
     * 刷新字典缓存
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxJson<Void> refreshCache() {
        dictTypeService.resetDictCache();
        return AjaxJson.getSuccess();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxJson<List<SysDictType>> optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return AjaxJson.getSuccessData(dictTypes);
    }
}
