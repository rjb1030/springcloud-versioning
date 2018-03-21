package com.xinyunlian.springcloud.versioning.config;


import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.xinyunlian.springcloud.versioning.context.EurekaServerExtractor;
import com.xinyunlian.springcloud.versioning.context.VersioningInitializingBean;
import com.xinyunlian.springcloud.versioning.feign.VersioningFeighConfiguration;
import com.xinyunlian.springcloud.versioning.ribbon.DefaultRibbonConnectionPoint;
import com.xinyunlian.springcloud.versioning.ribbon.LoadBalanceRequestTrigger;
import com.xinyunlian.springcloud.versioning.ribbon.VersionRibbonConnectionPoint;
import com.xinyunlian.springcloud.versioning.ribbon.VersioningClientHttpRequestIntercptor;
import com.xinyunlian.springcloud.versioning.ribbon.loadbalancer.VersioningZoneAvoidanceRule;
import com.xinyunlian.springcloud.versioning.strategy.MetadataVersionExtractor;
import com.xinyunlian.springcloud.versioning.strategy.RequestVersionExtractor;
import com.xinyunlian.springcloud.versioning.strategy.ServerVersionMetadataProperties;
import com.xinyunlian.springcloud.versioning.utils.VersioningConstants;
import com.xinyunlian.springcloud.versioning.zuul.VersioningZuulConfiguration;
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


    public static class UnUseVersioningIRule{

    }


    @Autowired(required = false)
    private IClientConfig config;

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


    @Bean
    @ConditionalOnMissingBean(value = {VersioningConfiguration.UnUseVersioningIRule.class})
    public IRule ribbonRule() {
        VersioningZoneAvoidanceRule rule = new VersioningZoneAvoidanceRule();
        rule.initWithNiwsConfig(config);
        return rule;
    }


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
