package com.graydeploy.springcloud.versioning.config;

import com.graydeploy.springcloud.versioning.ribbon.loadbalancer.VersioningZoneAvoidanceRule;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by rujianbin on 2018/3/26.
 */
public class FeignClientConfigduration {



    /**
     * 默认使用RibbonClientConfiguration 配置
     * https://segmentfault.com/a/1190000010486459
     * https://www.jianshu.com/p/19bcd9acf559
     * https://blog.csdn.net/yyz335258/article/details/77863145
     * feign的每个客户端都会按照RibbonClientConfiguration创建一份配置，故IClientConfig IRule ILoadBalancer 等都是不同客户端不同配置的
     * 如果单纯的@Bean全局配置，则会导致所有feign客户端都使用同一个rule实例，而rule又依赖了ILoadBalancer维护着对应可用的服务列表。多个feign客户端就会产生覆盖现象
     * 故需要指定每个feign客户端的rule规则实例
     * @RibbonClients(value = {
     *      @RibbonClient(name = "rujianbin-graydeploy-provider",configuration = FeignClientConfigduration.class),
     *      @RibbonClient(name = "rujianbin-graydeploy-provider2",configuration = FeignClientConfigduration.class)
     *      })
     *      或者使用配置指定
     *      service-B.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = {VersioningConfiguration.UnUseVersioningIRule.class})
    public IRule ribbonRule(IClientConfig config) {
        VersioningZoneAvoidanceRule rule = new VersioningZoneAvoidanceRule();
        rule.initWithNiwsConfig(config);
        return rule;
    }
}
