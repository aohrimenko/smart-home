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

        http.securityMatcher("/service/**")
                .addFilterBefore(new ServiceAuthenticationFilter(serviceSecurityConfiguration.getApiKeyServiceHeader(), serviceSecurityConfiguration.getApiKeyValue()),
                        BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/service/**").permitAll();
                });
        return http.build();
    }

}
