package com.tuempresa.ordenes.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    
    @Value("${external.services.inventory.timeout:5000}")
    private int inventoryTimeout;
    
    @Value("${external.services.payment.timeout:5000}")
    private int paymentTimeout;
    
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);
        
        return new RestTemplate(factory);
    }
} 