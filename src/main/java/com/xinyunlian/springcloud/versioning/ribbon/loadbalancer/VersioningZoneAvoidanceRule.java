package com.xinyunlian.springcloud.versioning.ribbon.loadbalancer;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.xinyunlian.springcloud.versioning.context.VersioningAppContext;

import java.util.Map;


public class VersioningZoneAvoidanceRule extends ZoneAvoidanceRule {

    protected CompositePredicate versioningCompositePredicate;

    public VersioningZoneAvoidanceRule() {
        super();
        VersioningApiVersionPredicate apiVersionPredicate = new VersioningApiVersionPredicate(this);
        versioningCompositePredicate = CompositePredicate.withPredicates(super.getPredicate(),
                apiVersionPredicate).build();
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return versioningCompositePredicate;
    }

}
