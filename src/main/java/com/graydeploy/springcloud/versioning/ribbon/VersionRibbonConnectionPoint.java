package com.graydeploy.springcloud.versioning.ribbon;

import com.graydeploy.springcloud.versioning.context.ConnectPointContext;


public interface VersionRibbonConnectionPoint {

    void executeConnectPoint(ConnectPointContext connectPointContext);


    void shutdownconnectPoint();
}
