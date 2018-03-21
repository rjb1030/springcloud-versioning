package com.graydeploy.springcloud.versioning.zuul;

import com.netflix.zuul.http.ZuulServlet;
import com.graydeploy.springcloud.versioning.zuul.filter.VersioningPostZuulFilter;
import com.graydeploy.springcloud.versioning.zuul.filter.VersioningPreZuulFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = ZuulServlet.class)
public class VersioningZuulConfiguration {

    @Bean
    public VersioningPreZuulFilter versioningPreZuulFilter(){
        return new VersioningPreZuulFilter();
    }

    @Bean
    public VersioningPostZuulFilter versioningPostZuulFilter(){
        return new VersioningPostZuulFilter();
    }
}
