package com.bootcamp.productos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfig {

    @Value("${services.catalog.url}")
    private String catalogUrl;

    @Value("${services.discount.url}")
    private String discountUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public com.bootcamp.productos.client.catalog.api.DefaultApi catalogApiClient(RestTemplate restTemplate) {
        com.bootcamp.productos.client.catalog.ApiClient apiClient = new com.bootcamp.productos.client.catalog.ApiClient(restTemplate);
        apiClient.setBasePath(catalogUrl);
        return new com.bootcamp.productos.client.catalog.api.DefaultApi(apiClient);
    }

    @Bean
    public com.bootcamp.productos.client.discount.api.DefaultApi discountApiClient(RestTemplate restTemplate) {
        com.bootcamp.productos.client.discount.ApiClient apiClient = new com.bootcamp.productos.client.discount.ApiClient(restTemplate);
        apiClient.setBasePath(discountUrl);
        return new com.bootcamp.productos.client.discount.api.DefaultApi(apiClient);
    }
}