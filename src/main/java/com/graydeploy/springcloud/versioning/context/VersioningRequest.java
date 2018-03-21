package com.graydeploy.springcloud.versioning.context;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

public class VersioningRequest {
    private final String uri;
    private final String serviceId;
    private final String ip;
    private final MultiValueMap<String, String> params;
    private final MultiValueMap<String, String> headers;


    private VersioningRequest(String uri, String serviceId, String ip, MultiValueMap<String, String> params, MultiValueMap<String, String> headers) {
        this.uri = uri;
        this.serviceId = serviceId;
        this.params = params;
        this.headers = headers;
        this.ip = ip;

    }


    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String uri;
        private String serviceId;
        private String ip;
        private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        private MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        private Builder(){

        }



        public Builder ip(String ip){
            this.ip = ip;
            return this;
        }

        public Builder uri(String uri){
            this.uri = uri;
            return this;
        }

        public Builder serviceId(String serviceId){
            this.serviceId = serviceId;
            return this;
        }

        public Builder params(MultiValueMap<String, String> params){
            if(params!=null) {
                this.params = params;
            }
            return this;
        }

        public Builder addParams(Map<String, String> params){
            if(params!=null) {
                this.params.setAll(params);
            }
            return this;
        }

        public Builder addParameter(String key, String value){
            this.params.add(key, value);
            return this;
        }

        public Builder addMultiParams(Map<String,List<String>> params){
            if(params!=null) {
                this.params.putAll(params);
            }
            return this;
        }

        public Builder headers(MultiValueMap<String, String> headers){
            if(headers!=null) {
                this.headers = headers;
            }
            return this;
        }

        public Builder addHeaders(Map<String, String> headers){
            if(headers!=null) {
                this.headers.setAll(headers);
            }
            return this;
        }

        public Builder addMultiHeaders(Map<String,List<String>> headers){
            if(headers!=null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        public Builder addHeader(String key, String value){
            this.headers.add(key, value);
            return this;
        }


        public VersioningRequest build() {
            return new VersioningRequest(uri, serviceId, ip, params, headers);
        }
    }





    public String getUri() {
        return uri;
    }

    public String getServiceId() {
        return serviceId;
    }

    public MultiValueMap<String, String> getParams() {
        return params;
    }

    public MultiValueMap<String, String> getHeaders() {
        return headers;
    }


    public String getIp() {
        return ip;
    }
}
