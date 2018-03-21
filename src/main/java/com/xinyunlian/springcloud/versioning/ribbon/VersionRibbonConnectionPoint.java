package com.xinyunlian.springcloud.versioning.ribbon;

import com.xinyunlian.springcloud.versioning.context.ConnectPointContext;


public interface VersionRibbonConnectionPoint {

    void executeConnectPoint(ConnectPointContext connectPointContext);


    void shutdownconnectPoint();
}
