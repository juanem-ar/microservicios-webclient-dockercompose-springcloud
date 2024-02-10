package com.springcloud.orders_service.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.DefaultClientRequestObservationConvention;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    //se le agrega la anotacionloadbalanced para habilitar el equilibrado de carga automatico al realizar solicitudes HTTP a servicios registrados en un servidor Eureka
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(ObservationRegistry observationRegistry){
        //Este filtro agrega el bearear token en las solicitudes salientes dentro de un cliente webClient
        return WebClient.builder()
                .filter(new ServletBearerExchangeFilterFunction())
                .observationRegistry(observationRegistry)
                .observationConvention(new DefaultClientRequestObservationConvention()); //toma los metadatos desde el cliente, para poder tomar el Id de la traza del padre
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.build();
    }
}
