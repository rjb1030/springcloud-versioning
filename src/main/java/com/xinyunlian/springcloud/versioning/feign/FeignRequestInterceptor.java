package com.xinyunlian.springcloud.versioning.feign;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xinyunlian.springcloud.versioning.strategy.RequestVersionExtractor;
import com.xinyunlian.springcloud.versioning.web.RequestHeaderKeeper;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignRequestInterceptor.class);

        public FeignRequestInterceptor() {

        }

        @Override
        public void apply(RequestTemplate template) {
            if (!HystrixRequestContext.isCurrentThreadInitialized()){
                HystrixRequestContext.initializeContext();
            }
            String version= RequestHeaderKeeper.GRAY_VERSION.get();
            if(logger.isDebugEnabled()){
                logger.debug("feign RequestInterceptor  gray_version="+version);
            }
            template.header(RequestVersionExtractor.RequestHeaderVersionExtractor.VERSION, version);
        }

}
