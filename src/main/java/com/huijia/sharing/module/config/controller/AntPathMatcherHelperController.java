package com.huijia.sharing.module.config.controller;

import cn.hutool.core.util.StrUtil;
import com.huijia.sharing.core.util.AjaxJson;
import com.huijia.sharing.module.config.model.request.TestAntPathMatcherRequest;
import com.huijia.sharing.module.link.aspect.RefererCheckAspect;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaojun
 */
@Api(tags = "Ant Path Matcher 辅助 Controller")
@Slf4j
@RequestMapping("/admin")
@RestController
public class AntPathMatcherHelperController {

    @Resource
    private RefererCheckAspect refererCheckAspect;

    @PostMapping("/ant-path-test")
    public AjaxJson<Boolean> clientIp(@RequestBody TestAntPathMatcherRequest testAntPathMatcherRequest) {
        if (testAntPathMatcherRequest == null) {
            return AjaxJson.getSuccessData(false);
        }
        String testPath = testAntPathMatcherRequest.getTestPath();
        String antPath = testAntPathMatcherRequest.getAntPath();
        if (StrUtil.isBlank(testPath) || StrUtil.isBlank(antPath)) {
            return AjaxJson.getSuccessData(false);
        }
        List<String> refererValueList = StrUtil.split(antPath, '\n');
        return AjaxJson.getSuccessData(refererCheckAspect.containsPathMatcher(refererValueList, testPath));
    }

}