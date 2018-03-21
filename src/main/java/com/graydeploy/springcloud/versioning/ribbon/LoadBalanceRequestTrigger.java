package com.graydeploy.springcloud.versioning.ribbon;

import com.graydeploy.springcloud.versioning.context.ConnectPointContext;

public interface LoadBalanceRequestTrigger {


    boolean shouldExecute();

    void before(ConnectPointContext connectPointContext);

    void after(ConnectPointContext connectPointContext);
}
