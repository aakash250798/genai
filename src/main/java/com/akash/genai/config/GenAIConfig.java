package com.akash.genai.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class GenAIConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
//                .setConnectTimeout(Duration.ofMillis(5000)) // Connection timeout
//                .setReadTimeout(Duration.ofMillis(10000))  // Read timeout
//                .build();
                .connectTimeout(Duration.ofMillis(5000))
                .readTimeout(Duration.ofMillis(50000)).build();
    }
}
