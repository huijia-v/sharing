package com.huijia.sharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author huijia
 * @date 2024/12/16 9:17
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@ServletComponentScan(basePackages = {"com.huijia.sharing.core.filter", "com.huijia.sharing.module.storage.filter"})
@ComponentScan(basePackages = "com.huijia.sharing.*")
public class SharingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SharingApplication.class, args);
    }
}
