package com.smarthome.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfiguration {

    private String apiKeyServiceHeader;

    private Map<String, ServiceConfig> services;

    public String getServiceUrl(final String serviceName) {

        ServiceConfig serviceConfig = services.get(serviceName);

        return serviceConfig != null ? serviceConfig.getUrl() : null;
    }

    public String getServiceHealthUrl(final String serviceName) {

        ServiceConfig serviceConfig = services.get(serviceName);

        if (serviceConfig == null) {
            return null;
        }

        return serviceConfig.getUrl() + serviceConfig.getHealthEndpoint();
    }

    public String getServiceApiKey(final String serviceName) {

        ServiceConfig serviceConfig = services.get(serviceName);

        return serviceConfig != null ? serviceConfig.getApiKeyValue() : null;
    }

    @Getter
    @Setter
    public static class ServiceConfig {

        private String url;

        private String apiKeyValue;

        private String healthEndpoint;

    }

}
