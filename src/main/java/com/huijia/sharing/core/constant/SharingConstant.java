package com.huijia.sharing.core.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * ZFile 常量
 *
 * @author zhaojun
 */
@Configuration
public class SharingConstant {

    public static final Character PATH_SEPARATOR_CHAR = '/';

    public static final String PATH_SEPARATOR = "/";

    public static final String TOKEN = "sharing-token";

    public static final Integer ISPUBLIC = 1;

    /**
     * 最大支持文本文件大小为 ? KB 的文件内容.
     */
    public static Long TEXT_MAX_FILE_SIZE_KB = 100L;

    @Autowired(required = false)
    public void setTextMaxFileSizeMb(@Value("${sharing.preview.text.maxFileSizeKb}") Long maxFileSizeKb) {
        SharingConstant.TEXT_MAX_FILE_SIZE_KB = maxFileSizeKb;
    }

}