package com.graydeploy.springcloud.versioning.feign;

import com.graydeploy.springcloud.versioning.context.ConnectPointContext;
import com.graydeploy.springcloud.versioning.context.VersioningAppContext;
import com.graydeploy.springcloud.versioning.utils.WebUtils;
import com.graydeploy.springcloud.versioning.web.RequestIpKeeper;
import com.graydeploy.springcloud.versioning.context.VersioningRequest;
import feign.Client;
import feign.Request;
import feign.Response;

import java.io.IOException;
import java.net.URI;


public class VersioningFeignClient implements Client {

    private Client delegate;

    public VersioningFeignClient(Client delegate){
        this.delegate=delegate;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        URI uri = URI.create(request.url());
        VersioningRequest.Builder builder = VersioningRequest.builder()
                .serviceId(uri.getHost())
                .uri(uri.getPath())
                .ip(RequestIpKeeper.getRequestIp())
                .addMultiParams(WebUtils.getQueryParams(uri.getQuery()));

        request.headers().entrySet().forEach(entry ->{
            for (String v : entry.getValue()) {
                builder.addHeader(entry.getKey(), v);
            }
        });

        ConnectPointContext connectPointContext = ConnectPointContext.builder().versionRequest(builder.build()).build();
        try {
            VersioningAppContext.getVersionRibbonConnectionPoint().executeConnectPoint(connectPointContext);
            return delegate.execute(request, options);
        }finally {
            VersioningAppContext.getVersionRibbonConnectionPoint().shutdownconnectPoint();
        }
    }
}
