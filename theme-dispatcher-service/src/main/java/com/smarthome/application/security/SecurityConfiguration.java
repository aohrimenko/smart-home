package com.smarthome.application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private ServiceSecurityConfiguration serviceSecurityConfiguration;

    @Bean
    @Order(1)
    public SecurityFilterChain filterInternalChain(HttpSecurity http) throws Exception {
        // the security chain will filter anything toward /device/* or /health with our custom filter
        // thus requiring it to have the appropriate api-key set.
        // if not - FORBIDDEN will be returned instead.
        http.securityMatcher("/device/**", "/health")
                .addFilterBefore(new ServiceAuthenticationFilter(serviceSecurityConfiguration.getApiKeyServiceHeader(), serviceSecurityConfiguration.getApiKeyValue()),
                        BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/device/**", "/health").permitAll();
                });
        return http.build();
    }

}
