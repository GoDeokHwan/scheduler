package com.example.scheduler.api.config.securiy;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "scheduler.jwt")
public class JwtProperties {
    private String header;

    private String secret;

    private Integer expiration;

    private String authPath;

    private String authRefresh;

    private String issuer;

    private Integer refresh;
}
