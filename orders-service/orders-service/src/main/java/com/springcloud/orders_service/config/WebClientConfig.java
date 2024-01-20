package com.springcloud.orders_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //se le agrega la anotacionloadbalanced para habilitar el equilibrado de carga automatico al realizar solicitudes HTTP a servicios registrados en un servidor Eureka
    @Bean
    @LoadBalanced
    public WebClient.Builder webClient(){
        return WebClient.builder();
    }
}
