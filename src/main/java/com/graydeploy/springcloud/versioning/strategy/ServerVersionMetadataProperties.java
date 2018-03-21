package com.graydeploy.springcloud.versioning.strategy;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashMap;
import java.util.Map;


@RefreshScope
@ConfigurationProperties(prefix = "versioning.server-version")
public class ServerVersionMetadataProperties {

    Map<String, Object> metadata = new HashMap();

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

}
