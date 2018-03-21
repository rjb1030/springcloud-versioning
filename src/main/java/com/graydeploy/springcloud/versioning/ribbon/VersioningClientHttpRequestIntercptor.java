package com.graydeploy.springcloud.versioning.ribbon;


import com.graydeploy.springcloud.versioning.context.ConnectPointContext;
import com.graydeploy.springcloud.versioning.context.VersioningAppContext;
import com.graydeploy.springcloud.versioning.utils.WebUtils;
import com.graydeploy.springcloud.versioning.context.VersioningRequest;
import com.graydeploy.springcloud.versioning.web.RequestIpKeeper;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;


/**
 * 用于@LoadBalance 标记的 RestTemplate，主要作用是用来获取request的相关信息，为后面的路由提供数据基础。
 */
public class VersioningClientHttpRequestIntercptor implements ClientHttpRequestInterceptor{
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        URI uri = request.getURI();
        VersioningRequest versioningRequest = VersioningRequest.builder()
                .serviceId(uri.getHost())
                .uri(uri.getPath())
                .ip(RequestIpKeeper.getRequestIp())
                .addMultiHeaders(request.getHeaders())
                .addMultiParams(WebUtils.getQueryParams(uri.getQuery()))
                .build();

        ConnectPointContext connectPointContext = ConnectPointContext.builder().versionRequest(versioningRequest).build();
        try {
            VersioningAppContext.getVersionRibbonConnectionPoint().executeConnectPoint(connectPointContext);
            return execution.execute(request, body);
        }finally {
            VersioningAppContext.getVersionRibbonConnectionPoint().shutdownconnectPoint();
        }
    }


}
