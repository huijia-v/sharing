package com.huijia.sharing.module.storage.service.impl;

import com.huijia.sharing.module.storage.model.enums.StorageTypeEnum;
import com.huijia.sharing.module.storage.model.param.SharePointParam;
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
public class SharePointServiceImpl extends AbstractSharePointServiceBase<SharePointParam> {

    @Value("${sharing.onedrive.clientId}")
    protected String clientId;

    @Value("${sharing.onedrive.redirectUri}")
    protected String redirectUri;

    @Value("${sharing.onedrive.clientSecret}")
    protected String clientSecret;

    @Value("${sharing.onedrive.scope}")
    protected String scope;

    @Override
    public StorageTypeEnum getStorageTypeEnum() {
        return StorageTypeEnum.SHAREPOINT_DRIVE;
    }

    @Override
    public String getGraphEndPoint() {
        return "graph.microsoft.com";
    }

    @Override
    public String getAuthenticateEndPoint() {
        return "login.microsoftonline.com";
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