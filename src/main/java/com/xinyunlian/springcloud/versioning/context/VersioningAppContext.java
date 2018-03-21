package com.xinyunlian.springcloud.versioning.context;

import com.netflix.loadbalancer.Server;
import com.xinyunlian.springcloud.versioning.ribbon.VersionRibbonConnectionPoint;

import java.util.Map;


public class VersioningAppContext {

    private static VersionRibbonConnectionPoint versionRibbonConnectionPoint;
    private static EurekaServerExtractor eurekaServerExtractor;
    private static String localIp;

    public static VersionRibbonConnectionPoint getVersionRibbonConnectionPoint() {
        return versionRibbonConnectionPoint;
    }

    public static void setVersionRibbonConnectionPoint(VersionRibbonConnectionPoint versionRibbonConnectionPoint) {
        VersioningAppContext.versionRibbonConnectionPoint = versionRibbonConnectionPoint;
    }

    public static EurekaServerExtractor getEurekaServerExtractor() {
        return eurekaServerExtractor;
    }

    public static void setEurekaServerExtractor(EurekaServerExtractor eurekaServerExtractor) {
        VersioningAppContext.eurekaServerExtractor = eurekaServerExtractor;
    }

    public static Map<String, String> getServerMetadata(String serviceId, Server server) {
        return eurekaServerExtractor.getServerMetadata(serviceId, server);
    }

    public static String getLocalIp() {
        return localIp;
    }

    public static void setLocalIp(String localIp) {
        VersioningAppContext.localIp = localIp;
    }
}
