package com.graydeploy.springcloud.versioning.config;


import com.graydeploy.springcloud.versioning.zuul.VersioningZuulConfiguration;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ClientConfigEnabledRoundRobinRule;
import com.netflix.loadbalancer.IRule;
import com.graydeploy.springcloud.versioning.context.EurekaServerExtractor;
import com.graydeploy.springcloud.versioning.context.VersioningInitializingBean;
import com.graydeploy.springcloud.versioning.feign.VersioningFeighConfiguration;
import com.graydeploy.springcloud.versioning.ribbon.DefaultRibbonConnectionPoint;
import com.graydeploy.springcloud.versioning.ribbon.LoadBalanceRequestTrigger;
import com.graydeploy.springcloud.versioning.ribbon.VersionRibbonConnectionPoint;
import com.graydeploy.springcloud.versioning.ribbon.VersioningClientHttpRequestIntercptor;
import com.graydeploy.springcloud.versioning.ribbon.loadbalancer.VersioningZoneAvoidanceRule;
import com.graydeploy.springcloud.versioning.strategy.MetadataVersionExtractor;
import com.graydeploy.springcloud.versioning.strategy.RequestVersionExtractor;
import com.graydeploy.springcloud.versioning.strategy.ServerVersionMetadataProperties;
import com.graydeploy.springcloud.versioning.utils.VersioningConstants;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;


@Configuration
@EnableConfigurationProperties({ServerVersionMetadataProperties.class})
@AutoConfigureBefore({VersioningFeighConfiguration.class, VersioningZuulConfiguration.class})
@Import(XylWebMvcConfiguration.class)
public class VersioningConfiguration {

    @Autowired(required = false)
    private IClientConfig config;

    public static class UnUseVersioningIRule{

    }


    @Autowired
    private SpringClientFactory springClientFactory;



    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new VersioningClientHttpRequestIntercptor());
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public EurekaServerExtractor eurekaServerExtractor(){
        return new EurekaServerExtractor(springClientFactory);
    }

//    /**
//     * https://segmentfault.com/a/1190000010486459
//     * https://www.jianshu.com/p/19bcd9acf559
//     * https://blog.csdn.net/yyz335258/article/details/77863145
//     * feign的每个客户端都会按照RibbonClientConfiguration创建一份配置，故IClientConfig IRule ILoadBalancer 等都是不同客户端不同配置的
//     * 此处做了全局配置后会导致feign共用一个IRule实例，IRule又依赖着ILoadBalancer（客户端对应服务的负载列表），会导致负载列表被覆盖
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean(value = {VersioningConfiguration.UnUseVersioningIRule.class})
//    public IRule ribbonRule() {
////        RoundRobinRule rule = new RoundRobinRule();
//////        rule.initWithNiwsConfig(config);
////        return rule;
//        VersioningZoneAvoidanceRule rule = new VersioningZoneAvoidanceRule();
//        rule.initWithNiwsConfig(config);
//        return rule;
//    }


    /**
     * 消费者路由版本的提取策略，默认走配置文件
     * 组件支持策略：
     *  1. URL query参数version路由，对应策略RequestVersionExtractor.Default()
     *  2. Header gray_version参数路由，对应策略RequestHeaderVersionExtractor
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestVersionExtractor requestVersionExtractor(){
        return new MetadataVersionExtractor();
    }


    @Bean
    @ConditionalOnMissingBean
    public VersionRibbonConnectionPoint versionRibbonConnectionPoint(
            RequestVersionExtractor requestVersionExtractor,
            @Autowired(required = false) List<LoadBalanceRequestTrigger> requestTriggerList){
        if(requestTriggerList!=null){
            requestTriggerList = Collections.EMPTY_LIST;
        }
        return new DefaultRibbonConnectionPoint(requestVersionExtractor, requestTriggerList);
    }

    @Bean
    @Order(value = VersioningConstants.INITIALIZING_ORDER)
    public VersioningInitializingBean versioningInitializingBean(){
        return new VersioningInitializingBean();
    }

}
