package com.huijia.sharing.module.storage.service.impl;

import com.huijia.sharing.module.storage.model.enums.StorageTypeEnum;
import com.huijia.sharing.module.storage.model.param.SharePointChinaParam;
import com.huijia.sharing.module.storage.service.base.AbstractSharePointServiceBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author zhaojun
 */
@Service
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SharePointChinaServiceImpl extends AbstractSharePointServiceBase<SharePointChinaParam> {

    @Value("${sharing.onedrive-china.clientId}")
    private String clientId;

    @Value("${sharing.onedrive-china.redirectUri}")
    private String redirectUri;

    @Value("${sharing.onedrive-china.clientSecret}")
    private String clientSecret;

    @Value("${sharing.onedrive-china.scope}")
    private String scope;

    @Override
    public StorageTypeEnum getStorageTypeEnum() {
        return StorageTypeEnum.SHAREPOINT_DRIVE_CHINA;
    }

    @Override
    public String getGraphEndPoint() {
        return "microsoftgraph.chinacloudapi.cn";
    }

    @Override
    public String getAuthenticateEndPoint() {
        return "login.partner.microsoftonline.cn";
    }
    
    @Override
    public String getClientId() {
        if (param == null || param.getClientId() == null) {
            return clientId;
        }
        return param.getClientId();
    }
    
    @Override
    public String getRedirectUri() {
        if (param == null || param.getRedirectUri() == null) {
            return redirectUri;
        }
        return param.getRedirectUri();
    }
    
    @Override
    public String getClientSecret() {
        if (param == null || param.getClientSecret() == null) {
            return clientSecret;
        }
        return param.getClientSecret();
    }
    
    @Override
    public String getScope() {
        return scope;
    }

}