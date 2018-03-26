package com.graydeploy.springcloud.versioning.context;

import com.graydeploy.springcloud.versioning.ribbon.VersionRibbonConnectionPoint;
import com.netflix.loadbalancer.IRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class VersioningInitializingBean implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(VersioningInitializingBean.class);


    private ApplicationContext ctx;

    @Override
    public void afterPropertiesSet() {
        VersioningAppContext.setVersionRibbonConnectionPoint(ctx.getBean(VersionRibbonConnectionPoint.class));
        VersioningAppContext.setEurekaServerExtractor(ctx.getBean(EurekaServerExtractor.class));
        setLocalIp();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }


    /**
     * 设置本机ip
     */
    private void setLocalIp(){
        try {
            VersioningAppContext.setLocalIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            logger.error("[IpHelper-getIpAddr] IpHelper error.", e);
        }
    }
}
