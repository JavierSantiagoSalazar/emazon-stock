package com.pragma.emazon_stock.infrastructure.configuration.bean;

import com.pragma.emazon_stock.infrastructure.feing.CustomErrorDecoder;
import com.pragma.emazon_stock.infrastructure.feing.FeignClientInterceptor;
import feign.Client;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public FeignClientInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor();
    }

    @Bean
    public Client feignClient() {
        return new ApacheHttpClient();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

}
