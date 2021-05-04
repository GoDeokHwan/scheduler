package com.example.scheduler.api.config;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "db.common")
public class PropertiesLoader {

    @NotNull
    private Properties hikari;

    @NotNull
    private Properties jpa;
}
