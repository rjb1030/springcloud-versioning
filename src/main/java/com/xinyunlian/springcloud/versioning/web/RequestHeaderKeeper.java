package com.xinyunlian.springcloud.versioning.web;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import com.xinyunlian.springcloud.versioning.strategy.RequestVersionExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rujianbin on 2018/3/21.
 */
public class RequestHeaderKeeper {

    public static final HystrixRequestVariableDefault<String> GRAY_VERSION = new HystrixRequestVariableDefault<String>();

    public static class HeaderKeeperInterceptor  extends HandlerInterceptorAdapter {

        private static final Logger logger = LoggerFactory.getLogger(HeaderKeeperInterceptor.class);

        @Override
        public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
            String gray_version = httpServletRequest.getHeader(RequestVersionExtractor.RequestHeaderVersionExtractor.VERSION);
            if(logger.isDebugEnabled()){
                logger.debug("HeaderKeeperInterceptor请求拦截 gray_version ="+gray_version);
            }
            if(!StringUtils.isEmpty(gray_version)){
                if (!HystrixRequestContext.isCurrentThreadInitialized()) {
                    HystrixRequestContext.initializeContext();
                }
                RequestHeaderKeeper.GRAY_VERSION.set(gray_version);
            }

            return true;
        }
    }
}
