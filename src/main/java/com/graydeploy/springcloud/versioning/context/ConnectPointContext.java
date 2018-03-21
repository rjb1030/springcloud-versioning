package com.graydeploy.springcloud.versioning.context;

public class ConnectPointContext {

    public static final ThreadLocal<ConnectPointContext> contextLocal = new ThreadLocal<>();

    private String apiVersion;
    private VersioningRequest versionRequest;
    private Throwable excption;

    private ConnectPointContext(VersioningRequest versionRequest) {
        this.versionRequest = versionRequest;
    }

    public VersioningRequest getVersionRequest() {
        return versionRequest;
    }

    public void setVersionRequest(VersioningRequest versionRequest) {
        this.versionRequest = versionRequest;
    }

    public Throwable getExcption() {
        return excption;
    }

    public void setExcption(Throwable excption) {
        this.excption = excption;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private VersioningRequest versionRequest;

        private Builder() {

        }


        public Builder versionRequest(VersioningRequest versionRequest) {
            this.versionRequest = versionRequest;
            return this;
        }

        public ConnectPointContext build() {
            ConnectPointContext context = new ConnectPointContext(versionRequest);
            ConnectPointContext.contextLocal.set(context);
            return context;
        }
    }


    public static ConnectPointContext getContextLocal() {
        return contextLocal.get();
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }
}
