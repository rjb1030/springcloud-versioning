package com.graydeploy.springcloud.versioning.ribbon.loadbalancer;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;
import com.graydeploy.springcloud.versioning.context.ConnectPointContext;
import com.graydeploy.springcloud.versioning.context.VersioningAppContext;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.Map;


public class VersioningApiVersionPredicate extends AbstractServerPredicate {


    public VersioningApiVersionPredicate(VersioningZoneAvoidanceRule rule) {
        super(rule);
    }

    @Override
    public boolean apply(PredicateKey input) {
        VersioningLoadBalancerKey loadBalancerKey = getVersioningLoadBalancerKey(input);
        if (loadBalancerKey != null && !StringUtils.isEmpty(loadBalancerKey.getApiVersion())) {
            Map<String, String> serverMetadata = VersioningAppContext.getServerMetadata(loadBalancerKey.getServiceId(), input.getServer());
            String versions = serverMetadata.get("versions");
            return matchVersion(versions, loadBalancerKey.getApiVersion());
        }
        return true;
    }

    private VersioningLoadBalancerKey getVersioningLoadBalancerKey(PredicateKey input) {
        ConnectPointContext connectPointContext = ConnectPointContext.getContextLocal();
        if(connectPointContext!=null){
            String apiVersion = connectPointContext.getApiVersion();
            if(!StringUtils.isEmpty(apiVersion)){
                return VersioningLoadBalancerKey.builder().apiVersion(apiVersion)
                        .serviceId(connectPointContext.getVersionRequest().getServiceId()).build();
            }
        }
        return null;
    }


    private boolean matchVersion(String serverVersions, String apiVersion) {
        if (StringUtils.isEmpty(serverVersions)) {
            return false;
        }
        String[] versions;
        if(serverVersions.contains(",")){
            versions = serverVersions.split(",");
        }else{
            versions = new String[]{serverVersions};
        }

        return ArrayUtils.contains(versions, apiVersion);
    }



}
