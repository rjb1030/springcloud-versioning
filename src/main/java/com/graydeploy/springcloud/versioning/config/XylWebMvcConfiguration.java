package com.graydeploy.springcloud.versioning.config;


import com.graydeploy.springcloud.versioning.web.RequestHeaderKeeper;
import com.graydeploy.springcloud.versioning.web.RequestIpKeeper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class XylWebMvcConfiguration extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestIpKeeper.IpKeepInterceptor());
        registry.addInterceptor(new RequestHeaderKeeper.HeaderKeeperInterceptor());
        super.addInterceptors(registry);
    }
}
