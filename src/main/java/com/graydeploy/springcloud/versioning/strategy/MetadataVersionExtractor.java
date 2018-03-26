package com.graydeploy.springcloud.versioning.strategy;

import com.graydeploy.springcloud.versioning.context.VersioningRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Created by rujianbin on 2018/3/21.
 */
@RefreshScope
public class MetadataVersionExtractor implements RequestVersionExtractor{

    @Autowired
    private ServerVersionMetadataProperties serverVersionMetadataProperties;

    @Override
    public String extractVersion(VersioningRequest versioningRequest) {
        if(serverVersionMetadataProperties!=null && serverVersionMetadataProperties.getMetadata()!=null && !serverVersionMetadataProperties.getMetadata().isEmpty()){
            Object version = serverVersionMetadataProperties.getMetadata().get(versioningRequest.getServiceId());
            if(version!=null){
                return version.toString();
            }
        }
        return null;
    }
}
