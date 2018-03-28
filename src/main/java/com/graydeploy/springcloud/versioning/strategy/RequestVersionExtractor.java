package com.graydeploy.springcloud.versioning.strategy;

import com.graydeploy.springcloud.versioning.context.VersioningRequest;

public interface RequestVersionExtractor {

    String extractVersion(VersioningRequest versioningRequest);


    class Default implements RequestVersionExtractor{
        private static final String VERSION = "rq_version";

        @Override
        public String extractVersion(VersioningRequest versioningRequest) {
            return versioningRequest.getParams().getFirst(VERSION);
        }
    }

    class RequestHeaderVersionExtractor implements RequestVersionExtractor{

        public static final String VERSION = "rq_version";

        @Override
        public String extractVersion(VersioningRequest versioningRequest) {
            return versioningRequest.getHeaders().getFirst(VERSION);
        }
    }

}
