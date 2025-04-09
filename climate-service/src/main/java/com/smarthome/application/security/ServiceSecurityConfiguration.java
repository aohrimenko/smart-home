package com.smarthome.application.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * This is a configuration class containing all the services "sensitive"-ish data.
 * this class is needed so we can get the current configuration or the health state of any underlying micro-services
 * since those services will be protected by a security chain, that would require an api-key header in the requests.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class ServiceSecurityConfiguration {

    private String apiKeyServiceHeader;

    private String apiKeyValue;

    private Map<String, ServiceConfig> services;

    public String getServiceUrl(final String serviceName) {
        return services.get(serviceName).getUrl();
    }

    public String getServiceHealthEndpoint(final String serviceName) {
        return services.get(serviceName).getHealthEndpoint();
    }

    public String getServiceApiKey(final String serviceName) {
        return services.get(serviceName).getApiKeyValue();
    }

    @Getter
    @Setter
    public static class ServiceConfig {

        private String url;

        private String apiKeyValue;

        private String healthEndpoint;

    }

}
