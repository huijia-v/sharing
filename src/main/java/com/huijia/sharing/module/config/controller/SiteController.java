package com.huijia.sharing.module.config.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.huijia.sharing.core.config.SharingProperties;
import com.huijia.sharing.core.util.AjaxJson;
import com.huijia.sharing.module.config.model.dto.SystemConfigDTO;
import com.huijia.sharing.module.config.model.result.SiteConfigResult;
import com.huijia.sharing.module.config.service.SystemConfigService;
import com.huijia.sharing.module.storage.model.request.base.FileListConfigRequest;
import com.huijia.sharing.module.storage.model.result.StorageSourceConfigResult;
import com.huijia.sharing.module.storage.service.StorageSourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 站点基础模块接口
 *
 * @author zhaojun
 */
@Api(tags = "站点基础模块")
@ApiSort(1)
@Slf4j
@RequestMapping("/api/site")
@RestController
public class SiteController {

	@Resource
	private SharingProperties sharingProperties;

	@Resource
	private StorageSourceService storageSourceService;

	@Resource
	private SystemConfigService systemConfigService;

	@ApiOperationSupport(order = 1)
	@ApiOperation(value = "获取站点全局设置", notes = "获取站点全局设置, 包括是否页面布局、列表尺寸、公告、配置信息")
	@GetMapping("/config/global")
	public AjaxJson<SiteConfigResult> globalConfig() {
		SystemConfigDTO systemConfig = systemConfigService.getSystemConfig();

		SiteConfigResult siteConfigResult = new SiteConfigResult();
		BeanUtils.copyProperties(systemConfig, siteConfigResult);

		siteConfigResult.setDebugMode(sharingProperties.isDebug());
		return AjaxJson.getSuccessData(siteConfigResult);
	}


	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "获取存储源设置", notes = "获取某个存储源的设置信息, 包括是否启用, 名称, 存储源类型, 存储源配置信息")
	@PostMapping("/config/storage")
	public AjaxJson<StorageSourceConfigResult> storageList(@Valid @RequestBody FileListConfigRequest fileListConfigRequest) {
		StorageSourceConfigResult storageSourceConfigResult = storageSourceService.getStorageConfigSource(fileListConfigRequest);
		return AjaxJson.getSuccessData(storageSourceConfigResult);
	}


	@ResponseBody
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "重置管理员密码", notes = "开启 debug 模式时，访问此接口会强制将管理员账户密码修改为 admin 123456, 并修改登录验证方式为图片验证码, 详见：https://docs.zfile.vip/#/question?id=reset-pwd")
	@GetMapping("/reset-password")
	public AjaxJson<?> resetPwd() {
		systemConfigService.resetAdminLoginInfo();
		return AjaxJson.getSuccess();
	}

}