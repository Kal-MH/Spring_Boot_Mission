package dev.kalmh.community.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient authClient() {
        return WebClient.create("http://localhost:8000");
    }
}
