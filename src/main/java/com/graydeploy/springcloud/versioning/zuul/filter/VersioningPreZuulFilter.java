package com.graydeploy.springcloud.versioning.zuul.filter;


import com.graydeploy.springcloud.versioning.context.ConnectPointContext;
import com.graydeploy.springcloud.versioning.context.VersioningAppContext;
import com.graydeploy.springcloud.versioning.context.VersioningRequest;
import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.util.stream.Collectors;

/**
 * 主要作用是用来获取request的相关信息，为后面的路由提供数据基础。
 */
public class VersioningPreZuulFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 10000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        VersioningRequest.Builder builder = VersioningRequest.builder()
                .serviceId((String)context.get(FilterConstants.SERVICE_ID_KEY))
                .uri((String)context.get(FilterConstants.REQUEST_URI_KEY))
                .ip(context.getZuulRequestHeaders().get(FilterConstants.X_FORWARDED_FOR_HEADER.toLowerCase()))
                .addMultiParams(context.getRequestQueryParams())
                .addHeaders(context.getZuulRequestHeaders())
                .addHeaders(context.getOriginResponseHeaders().stream().collect(Collectors.toMap(Pair::first, Pair::second)));
        context.getOriginResponseHeaders().forEach(pair-> builder.addHeader(pair.first(), pair.second()));

        ConnectPointContext connectPointContext = ConnectPointContext.builder().versionRequest(builder.build()).build();

        VersioningAppContext.getVersionRibbonConnectionPoint().executeConnectPoint(connectPointContext);
        return null;
    }

}
