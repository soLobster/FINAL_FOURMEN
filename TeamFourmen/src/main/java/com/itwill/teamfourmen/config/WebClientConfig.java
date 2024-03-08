package com.itwill.teamfourmen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class
WebClientConfig {

    @Bean
    // 기존 코드
//    public WebClient webClient() {
//        return WebClient.create();
//    }
    // WebClient의 최대 버퍼 크기를 증가시켜 API 요청으로 받아온 데이터를 처리 가능하게 하는 방법.
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
