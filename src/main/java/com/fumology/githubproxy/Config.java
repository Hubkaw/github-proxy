package com.fumology.githubproxy;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class Config {


    @Bean
    public RestClient githubRestClient(@Value("${github.base-url}") String baseURL) {
        return RestClient.builder()
                .baseUrl(baseURL)
                .build();
    }

}
