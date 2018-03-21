package com.xinyunlian.springcloud.versioning.feign;

import feign.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VersioningFeighClientsConfiguration {


    @Autowired
    private Client feignClient;

    @Bean
    public Client versioningFeignClient(){
        return new VersioningFeignClient(feignClient);
    }

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor(){
        return new FeignRequestInterceptor();
    }

}
