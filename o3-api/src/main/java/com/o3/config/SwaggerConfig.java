package com.o3.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "세금 환급 API 명세서",
                description = "세금 환급 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi o3Api() {
        return GroupedOpenApi.builder()
                .group("o3-api")
                .pathsToMatch("/**")
                .build();
    }

}
