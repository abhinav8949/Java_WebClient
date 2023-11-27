package com.java_mini2.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${myapp.api1Url}")
    private String api1Url;

    @Value("${myapp.api2Url}")
    private String api2Url;

    @Value("${myapp.api3Url}")
    private String api3Url;

    @Bean
    @Qualifier("api1WebClient")
    public WebClient api1WebClient() {
        return WebClient.builder()
                .baseUrl(api1Url)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }

    @Bean
    @Qualifier("api2WebClient")
    public WebClient api2WebClient() {
        return WebClient.builder()
                .baseUrl(api2Url)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }

    @Bean
    @Qualifier("api3WebClient")
    public WebClient api3WebClient() {
        return WebClient.builder()
                .baseUrl(api3Url)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                        .build())
                .build();
    }
}
