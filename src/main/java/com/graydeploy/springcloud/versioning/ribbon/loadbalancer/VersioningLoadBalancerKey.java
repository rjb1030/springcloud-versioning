package com.graydeploy.springcloud.versioning.ribbon.loadbalancer;


public class VersioningLoadBalancerKey {


    private String serviceId;
    private String apiVersion;


    private VersioningLoadBalancerKey() {

    }


    public String getApiVersion() {
        return apiVersion;
    }

    public String getServiceId() {
        return serviceId;
    }

    private void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static class Builder {

        private VersioningLoadBalancerKey build = new VersioningLoadBalancerKey();

        public Builder apiVersion(String apiVersion) {
            build.apiVersion = apiVersion;
            return this;
        }

        public Builder serviceId(String serviceId) {
            build.serviceId = serviceId;
            return this;
        }

        public VersioningLoadBalancerKey build() {
            return build;
        }

    }
}
