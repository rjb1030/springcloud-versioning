package com.graydeploy.springcloud.versioning.ribbon.loadbalancer;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.ZoneAvoidanceRule;


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
