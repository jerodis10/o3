package com.o3.security.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private final String secretKey;

    private final String tokenPrefix;

    private final Integer tokenExpirationAfterDays;

}
