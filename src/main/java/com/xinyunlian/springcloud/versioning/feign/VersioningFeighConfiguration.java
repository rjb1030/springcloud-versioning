package com.xinyunlian.springcloud.versioning.feign;

import com.netflix.loadbalancer.ILoadBalancer;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@ConditionalOnClass({ILoadBalancer.class, Feign.class})
@Configuration
@EnableFeignClients(defaultConfiguration = {VersioningFeighClientsConfiguration.class})
public class VersioningFeighConfiguration {

}
