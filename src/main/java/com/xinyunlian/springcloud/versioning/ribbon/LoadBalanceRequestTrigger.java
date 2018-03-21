package com.xinyunlian.springcloud.versioning.ribbon;

import com.xinyunlian.springcloud.versioning.context.ConnectPointContext;

public interface LoadBalanceRequestTrigger {


    boolean shouldExecute();

    void before(ConnectPointContext connectPointContext);

    void after(ConnectPointContext connectPointContext);
}
