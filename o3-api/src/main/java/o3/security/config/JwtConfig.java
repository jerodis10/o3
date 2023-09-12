package o3.security.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;


@Getter
@ConstructorBinding
@AllArgsConstructor
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secretKey;

    private String tokenPrefix;

    private Integer tokenExpirationAfterDays;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
