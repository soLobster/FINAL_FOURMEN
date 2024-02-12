package com.itwill.teamfourmen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
//    public WebClient webClient() {
//        return WebClient.create();
//    }
    public WebClient customWebClient() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024)) // Increase to 16MB
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }
}
