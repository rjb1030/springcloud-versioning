package com.xinyunlian.springcloud.versioning.web;


import com.xinyunlian.springcloud.versioning.context.VersioningAppContext;
import com.xinyunlian.springcloud.versioning.utils.WebUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class RequestIpKeeper {

    private static final ThreadLocal<String> ipLocal = new ThreadLocal<>();


    private static RequestIpKeeper INSTANCE = new RequestIpKeeper();

    private RequestIpKeeper(){

    }

    public static RequestIpKeeper instance(){
        return INSTANCE;
    }


    public void setIp(String ip){
        ipLocal.set(ip);
    }


    public String getIp(){
        return ipLocal.get();
    }


    public void clear(){
        ipLocal.remove();
    }



    public static String getRequestIp(){
        String ip = instance().getIp();
        if(StringUtils.isEmpty(ip)){
            ip = VersioningAppContext.getLocalIp();
        }
        return ip;
    }


    public static class IpKeepInterceptor extends HandlerInterceptorAdapter{

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String ip = WebUtils.getIpAddr(request);
            RequestIpKeeper.instance().setIp(ip);
            return super.preHandle(request, response, handler);
        }


        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            RequestIpKeeper.instance().clear();
            super.afterCompletion(request, response, handler, ex);
        }

    }

}
