package com.xinyunlian.springcloud.versioning.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.xinyunlian.springcloud.versioning.context.VersioningAppContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;


public class VersioningPostZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        VersioningAppContext.getVersionRibbonConnectionPoint().shutdownconnectPoint();
        return null;
    }
}
