package com.huijia.sharing.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhaojun
 */
@Data
@EnableConfigurationProperties
@Component
@ConfigurationProperties(prefix = "sharing")
public class SharingProperties {

	private boolean debug;

}