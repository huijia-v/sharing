package com.huijia.sharing.module.config.utils;

import com.huijia.sharing.core.util.RequestHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhaojun
 */
public class SpringMvcUtils {

    public static String getExtractPathWithinPattern() {
        HttpServletRequest httpServletRequest = RequestHolder.getRequest();
        String path = (String) httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        return apm.extractPathWithinPattern(bestMatchPattern, path);
    }

}
