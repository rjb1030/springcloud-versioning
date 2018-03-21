package com.xinyunlian.springcloud.versioning.strategy;

import com.xinyunlian.springcloud.versioning.context.VersioningRequest;

public interface RequestVersionExtractor {

    String extractVersion(VersioningRequest versioningRequest);


    class Default implements RequestVersionExtractor{
        private static final String VERSION = "version";

        @Override
        public String extractVersion(VersioningRequest versioningRequest) {
            return versioningRequest.getParams().getFirst(VERSION);
        }
    }

    class RequestHeaderVersionExtractor implements RequestVersionExtractor{

        public static final String VERSION = "gray_version";

        @Override
        public String extractVersion(VersioningRequest versioningRequest) {
            return versioningRequest.getHeaders().getFirst(VERSION);
        }
    }

}
