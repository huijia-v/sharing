package com.huijia.sharing.module.config.event;

import com.huijia.sharing.module.config.model.entity.SystemConfig;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaojun
 */
@Component
public class SystemConfigModifyHandler implements ISystemConfigModifyHandler {

    @Resource
    private List<ISystemConfigModifyHandler> handlers;

    public void modify(SystemConfig originalSystemConfig, SystemConfig newSystemConfig) {
        handlers.stream()
                .filter(handler -> handler.matches(originalSystemConfig.getName()))
                .forEach(handler -> handler.modify(originalSystemConfig, newSystemConfig));
    }

    @Override
    public boolean matches(String name) {
        return true;
    }

}
